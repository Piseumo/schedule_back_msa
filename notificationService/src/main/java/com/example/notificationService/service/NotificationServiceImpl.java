package com.example.notificationService.service;

import com.example.notificationService.constant.NotificationType;
import com.example.notificationService.entity.Notification;
import com.example.notificationService.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Map;

import static org.springframework.retry.policy.TimeoutRetryPolicy.DEFAULT_TIMEOUT;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;

    @Override
    public void sendFriendRequest(String username) {
        sendNotification(username, "You have a new friend request!", NotificationType.FRIEND_REQUEST);
    }

    @Override
    public void sendFriendAccept(String username) {
        sendNotification(username, "Your friend request has been accepted!", NotificationType.FRIEND_ACCEPT);
    }

    @Override
    public void sendMessage(String username) {
        sendNotification(username, "You have a new message!", NotificationType.MESSAGE);
    }

    @Override
    public void sendComment(String username) {
        sendNotification(username, "A new comment has been posted on your post.", NotificationType.COMMENT);
    }

    @Override
    public void sendFriendPost(String username) {
        sendNotification(username, "Your friend has posted something new!", NotificationType.FRIEND_NEW_POST);
    }

    private void sendNotification(String receiver, String content, NotificationType type) {
        Notification notification = Notification.builder()
                .notificationId(receiver + "_" + System.currentTimeMillis())
                .receiver(receiver)
                .content(content)
                .notificationType(type.getAlias())
                .url(type.getPath())
                .readYn('N')
                .deletedYn('N')
                .build();

        Map<String, SseEmitter> sseEmitters = notificationRepository.findAllEmitterStartsWithUsername(receiver);
        sseEmitters.forEach((key, sseEmitter) -> emitAndCacheEvent(sseEmitter, key, notification));
    }

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

