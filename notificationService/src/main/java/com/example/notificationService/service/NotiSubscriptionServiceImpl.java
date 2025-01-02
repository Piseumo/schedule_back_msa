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

@Service
@RequiredArgsConstructor
@Slf4j
public class NotiSubscriptionServiceImpl implements NotiSubscriptionService {

    private final NotificationRepository notificationRepository;

    /**
     * 사용자별 SSE 구독 생성
     */
    @Override
    public SseEmitter subscribe(String username, String lastEventId) {
        String emitterId = username + "_" + System.currentTimeMillis();
        SseEmitter sseEmitter = notificationRepository.subscribeSave(emitterId, new SseEmitter(Long.MAX_VALUE));

        // 클라이언트 연결 확인용 초기 이벤트 전송
        try {
            sseEmitter.send(SseEmitter.event()
                    .id(emitterId)
                    .name("sse")
                    .data("Connection Established"));
        } catch (IOException e) {
            log.error("Failed to send initial SSE event: {}", e.getMessage());
        }

        log.info("New SSE connection established: {}", emitterId);

        // SSE 연결 종료 시 처리
        sseEmitter.onCompletion(() -> handleEmitterCompletion(emitterId));
        sseEmitter.onTimeout(() -> handleEmitterTimeout(emitterId));
        sseEmitter.onError(e -> handleEmitterError(emitterId, e));

        // 연결 유지용 더미 알림 전송
        sendDummyNotification(username, emitterId);

        // 이전 이벤트 캐시 재전송
        if (!lastEventId.isEmpty()) {
            resendCachedEvents(username, lastEventId, sseEmitter);
        }

        return sseEmitter;
    }

    /**
     * 연결 유지용 더미 알림 전송
     */
    private void sendDummyNotification(String username, String emitterId) {
        Notification dummy = Notification.builder()
                .notificationId(emitterId)
                .receiver(username)
                .content("Connection Established")
                .notificationType(NotificationType.MESSAGE)
                .url(NotificationType.MESSAGE.getPath())
                .readYn('N')
                .deletedYn('N')
                .build();

        emitEventToClient(notificationRepository.findEmitterById(emitterId), emitterId, dummy);
    }

    /**
     * 이전 이벤트 캐시 재전송
     */
    private void resendCachedEvents(String username, String lastEventId, SseEmitter sseEmitter) {
        Map<String, Object> eventCaches = notificationRepository.findAllEventCacheStartsWithUsername(username);
        eventCaches.entrySet().stream()
                .filter(entry -> lastEventId.compareTo(entry.getKey()) < 0)
                .forEach(entry -> emitEventToClient(sseEmitter, entry.getKey(), entry.getValue()));
    }

    /**
     * 클라이언트로 이벤트 전송
     */
    private void emitEventToClient(SseEmitter sseEmitter, String emitterId, Object data) {
        try {
            sseEmitter.send(SseEmitter.event()
                    .id(emitterId)
                    .name("notification")
                    .data(data, MediaType.APPLICATION_JSON));
        } catch (IOException e) {
            notificationRepository.deleteEmitterById(emitterId);
            log.error("Failed to emit SSE event. Emitter removed: {}", emitterId, e);
        }
    }

    /**
     * 사용자별 이벤트 전송
     */
    public void sendEvent(String userName, String eventContent) {
        String emitterId = userName + "_" + System.currentTimeMillis();
        SseEmitter emitter = notificationRepository.findEmitterById(emitterId);
        if (emitter != null) {
            try {
                emitter.send(SseEmitter.event()
                        .name("notification")
                        .data(eventContent));
            } catch (IOException e) {
                notificationRepository.deleteEmitterById(emitterId);
                log.error("Failed to send event to user: {}", userName, e);
            }
        }
    }

    /**
     * Emitter 종료 핸들러
     */
    private void handleEmitterCompletion(String emitterId) {
        notificationRepository.deleteEmitterById(emitterId);
        log.info("SSE connection completed: {}", emitterId);
    }

    /**
     * Emitter 타임아웃 핸들러
     */
    private void handleEmitterTimeout(String emitterId) {
        notificationRepository.deleteEmitterById(emitterId);
        log.warn("SSE connection timed out: {}", emitterId);
    }

    /**
     * Emitter 오류 핸들러
     */
    private void handleEmitterError(String emitterId, Throwable error) {
        notificationRepository.deleteEmitterById(emitterId);
        log.error("SSE connection error: {}", emitterId, error);
    }
}
