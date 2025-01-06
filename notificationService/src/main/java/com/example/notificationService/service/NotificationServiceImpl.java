package com.example.notificationService.service;

import com.example.notificationService.constant.NotificationType;
import com.example.notificationService.entity.Notification;
import com.example.notificationService.repository.NotificationJPARepository;
import com.example.notificationService.repository.NotificationRepository;
import com.stoyanr.evictor.map.ConcurrentMapWithTimedEvictionDecorator;
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
    private final NotiSubscriptionService notiSubscriptionService;

    //친구 신청 알람
    @Transactional
    @Override
    public String sendFriendRequest(String userName, String friendName) {
        String emitterId = userName + "_" + System.currentTimeMillis();
        log.info("친구 알림 생성: userName={}, friendName={}", userName, friendName);
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
//        sendNotification(friendName, userName+"에게"+friendName+"님이 친구 요청을 보냈습니다", NotificationType.FRIEND_REQUEST);
        return notification.getContent();
    }

    @Transactional
    @Override
    public List<Notification> getUnreadNotifications(String userName) {
        return notificationJPARepository.findByReceiverAndReadYn(userName, 'N');
    }

    //친구 수락
//    @Transactional
//    @Override
//    public void sendFriendAccept(String friendName, String userName) {
//        sendNotification(userName, friendName+"께서 친구요청을 수락했습니다.", NotificationType.FRIEND_ACCEPT);
//    }
//
//    @Transactional
//    @Override
//    public void sendMessage(String username) {
//        sendNotification(username, "You have a new message!", NotificationType.MESSAGE);
//    }
//
//    @Transactional
//    @Override
//    public void sendComment(String username) {
//        sendNotification(username, "A new comment has been posted on your post.", NotificationType.COMMENT);
//    }

//    // 메세지 정보 전달
//    private void sendNotification(String username, String content, NotificationType type) {
//        log.info("Preparing to send notification to user: {}, content: {}", username, content);
//
//        // Notification 생성
//        Notification notification = Notification.builder()
//                .notificationId(username + "_" + System.currentTimeMillis())
//                .receiver(username)
//                .content(content)
//                .notificationType(type)
//                .url(type.getPath())
//                .readYn('N')
//                .deletedYn('N')
//                .build();
//
//        // Notification 저장
//        notificationRepository.saveEventCache(notification.getNotificationId(), notification);
//
//        // Receiver와 관련된 모든 Emitter 조회
//        List<SseEmitter> emitters = notificationRepository.findAllEmittersStartsWithUsername(username);
//
//        if (!emitters.isEmpty()) {
//            emitters.forEach(emitter ->notiSubscriptionService.emitEventToClient(emitter, notification.getNotificationId(), notification));
//        } else {
//            log.warn("No active SSE emitters found for user: {}", username);
//        }
//    }



    // 실제 메세지 전송 및 캐쉬 저장 하는 곳
    public void emitAndCacheEvent(SseEmitter emitter, String key, Notification notification) {
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

