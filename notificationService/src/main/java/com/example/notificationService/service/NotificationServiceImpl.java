package com.example.notificationService.service;

import com.example.notificationService.constant.NotificationType;
import com.example.notificationService.entity.Notification;
import com.example.notificationService.repository.NotificationJPARepository;
import com.example.notificationService.repository.NotificationRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.retry.policy.TimeoutRetryPolicy.DEFAULT_TIMEOUT;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;
    private final NotificationJPARepository notificationJPARepository;

    //친구 신청 알람
    @Transactional
    @Override
    public String sendFriendRequest(String userName, String friendName) {
        String emitterId = userName + "_" + System.currentTimeMillis();
        Notification notification = Notification.builder()
                .notificationId(emitterId)
                .receiver(userName)
                .content(friendName + "님이 친구 요청을 보냈습니다.")
                .notificationType(NotificationType.FRIEND_REQUEST)
                .url(NotificationType.FRIEND_REQUEST.getPath())
                .readYn('N')
                .deletedYn('N')
                .createdAt(LocalDateTime.now())
                .build();
        notificationJPARepository.save(notification);
        notificationRepository.saveEventCache(emitterId, notification);
        sendNotification(friendName, userName+"에게"+friendName+"님이 친구 요청을 보냈습니다", NotificationType.FRIEND_REQUEST);
        return notification.getContent();
    }

    //친구 수락
    @Transactional
    @Override
    public void sendFriendAccept(String friendName, String userName) {
        sendNotification(userName, friendName+"께서 친구요청을 수락했습니다.", NotificationType.FRIEND_ACCEPT);
    }

    @Transactional
    @Override
    public void sendMessage(String username) {
        sendNotification(username, "You have a new message!", NotificationType.MESSAGE);
    }

    @Transactional
    @Override
    public void sendComment(String username) {
        sendNotification(username, "A new comment has been posted on your post.", NotificationType.COMMENT);
    }

    @Transactional
    @Override
    public void sendFriendPost(String username) {
        sendNotification(username, "Your friend has posted something new!", NotificationType.FRIEND_NEW_POST);
    }

    // 메세지정보 전달
    private void sendNotification(String username, String content, NotificationType type) {
        // Notification 생성
        Notification notification = Notification.builder()
                .notificationId(username + "_" + System.currentTimeMillis())
                .receiver(username)
                .content(content)
                .notificationType(type)
                .url(type.getPath())
                .readYn('N')
                .deletedYn('N')
                .build();

        // Receiver로 시작하는 모든 Notification 조회
        List<Notification> notifications = notificationRepository.findAllEmitterStartsWithUsername(username);

        // Notification 리스트를 Map<String, SseEmitter>로 변환
        Map<String, SseEmitter> sseEmitters = notifications.stream()
                .collect(Collectors.toMap(
                        Notification::getNotificationId, // Key: Notification ID
                        n -> new SseEmitter(Long.MAX_VALUE) // Value: 새로운 SseEmitter 생성
                ));

        // SseEmitter로 알림 전송 및 캐시 저장
        sseEmitters.forEach((key, sseEmitter) -> emitAndCacheEvent(sseEmitter, key, notification));
    }


    // 실제 메세지 전송 및 캐쉬 저장 하는 곳
    private void emitAndCacheEvent(SseEmitter emitter, String key, Notification notification) {
        try {
            notificationRepository.saveEventCache(key, notification); // 캐시 저장
            emitter.send(SseEmitter.event()
                    .id(key)
                    .name("notification")
                    .data(notification, MediaType.APPLICATION_JSON));
        } catch (IOException e) {
            notificationRepository.deleteEmitterById(key);
        }
    }
}

