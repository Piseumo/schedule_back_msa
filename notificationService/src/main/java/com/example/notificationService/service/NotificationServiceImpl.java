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

    /**
     * SSE 구독 요청 처리
     */
    public SseEmitter subscribe(String username, String lastEventId) {
        String emitterId = username + "_" + System.currentTimeMillis();

        SseEmitter sseEmitter = notificationRepository.save(emitterId, new SseEmitter(DEFAULT_TIMEOUT));
        log.info("New emitter added: {}", sseEmitter);
        log.info("LastEventId: {}", lastEventId);

        // SSE 이벤트 제거 처리
        sseEmitter.onCompletion(() -> notificationRepository.deleteEmitterById(emitterId));
        sseEmitter.onTimeout(() -> notificationRepository.deleteEmitterById(emitterId));
        sseEmitter.onError((e) -> notificationRepository.deleteEmitterById(emitterId));

        // 연결 유지를 위한 더미 데이터 전송
        send(sseEmitter, emitterId, createDummyNotification(username));

        // 미수신된 알림 전송
        if (!lastEventId.isEmpty()) {
            Map<String, Object> eventCaches = notificationRepository.findAllEventCacheStartsWithUsername(username);
            eventCaches.entrySet().stream()
                    .filter(entry -> lastEventId.compareTo(entry.getKey()) < 0)
                    .forEach(entry -> emitEventToClient(sseEmitter, entry.getKey(), entry.getValue()));
        }

        return sseEmitter;
    }

    // 개별 메서드: 친구 신청 알림
    @Override
    public SseEmitter sendFriendRequest(String username) {
        sendNotification(username, "새로운 친구 요청이 있습니다.", NotificationType.FRIEND_REQUEST);
        return null;
    }

    // 개별 메서드: 친구 수락 알림
    @Override
    public SseEmitter sendFriendAccept(String username) {
        sendNotification(username, "친구 요청이 수락되었습니다!", NotificationType.FRIEND_ACCEPT);
        return null;
    }

    // 개별 메서드: 쪽지 알림
    @Override
    public SseEmitter sendMessage(String username) {
        sendNotification(username, "새로운 쪽지가 도착했습니다.", NotificationType.MESSAGE);
        return null;
    }

    // 개별 메서드: 댓글 알림
    @Override
    public SseEmitter sendComment(String username) {
        sendNotification(username, "새로운 댓글이 등록되었습니다.", NotificationType.COMMENT);
        return null;
    }

    // 개별 메서드: 친구 새 글 알림
    @Override
    public SseEmitter sendFriendPost(String username) {
        sendNotification(username, "친구가 새 글을 작성했습니다.", NotificationType.FRIEND_NEW_POST);
        return null;
    }

    /**
     * 공통 알림 전송 메서드
     */
    private void sendNotification(String receiver, String content, NotificationType type) {
        Notification notification = createNotification(receiver, content, type);

        Map<String, SseEmitter> sseEmitters = notificationRepository.findAllEmitterStartsWithUsername(receiver);
        sseEmitters.forEach((key, sseEmitter) -> {
            log.info("Sending notification: key={}, notification={}", key, notification);
            notificationRepository.saveEventCache(key, notification); // 캐시에 저장
            emitEventToClient(sseEmitter, key, notification); // 전송
        });
    }

    /**
     * 더미 데이터 생성 (503 방지)
     */
    private Notification createDummyNotification(String receiver) {
        return Notification.builder()
                .notificationId(receiver + "_" + System.currentTimeMillis())
                .receiver(receiver)
                .content("Dummy notification to keep the connection alive.")
                .notificationType(NotificationType.MESSAGE.getAlias())
                .url(NotificationType.MESSAGE.getPath())
                .readYn('N')
                .deletedYn('N')
                .build();
    }

    /**
     * 알림 객체 생성 (공통 메서드)
     */
    private Notification createNotification(String receiver, String content, NotificationType type) {
        return Notification.builder()
                .notificationId(receiver + "_" + System.currentTimeMillis())
                .receiver(receiver)
                .content(content)
                .notificationType(type.getAlias())
                .url(type.getPath())
                .readYn('N')
                .deletedYn('N')
                .build();
    }

    /**
     * SSE 이벤트 전송 로직
     */
    private void send(SseEmitter sseEmitter, String emitterId, Object data) {
        try {
            sseEmitter.send(SseEmitter.event()
                    .id(emitterId)
                    .name("sse")
                    .data(data, MediaType.APPLICATION_JSON));
        } catch (IOException e) {
            notificationRepository.deleteEmitterById(emitterId);
            sseEmitter.completeWithError(e);
        }
    }

    /**
     * 클라이언트로 알림 전송
     */
    private void emitEventToClient(SseEmitter sseEmitter, String emitterId, Object data) {
        try {
            send(sseEmitter, emitterId, data);
            notificationRepository.deleteEventCacheById(emitterId);
        } catch (Exception e) {
            notificationRepository.deleteEmitterById(emitterId);
            throw new RuntimeException("Connection Failed.", e);
        }
    }
}
