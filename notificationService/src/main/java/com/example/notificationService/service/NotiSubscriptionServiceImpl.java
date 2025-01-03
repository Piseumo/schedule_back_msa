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
import java.util.List;
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

        // 이전 이벤트 캐시 재전송
        if (!lastEventId.isEmpty()) {
            resendCachedEvents(username, lastEventId, sseEmitter);
        }

        return sseEmitter;
    }

    /**
     * 이전 이벤트 캐시 재전송
     */
     void resendCachedEvents(String username, String lastEventId, SseEmitter sseEmitter) {
        Map<String, Notification> eventCaches = notificationRepository.findAllEventCacheStartsWithUsername(username);
        eventCaches.entrySet().stream()
                .filter(entry -> lastEventId.compareTo(entry.getKey()) < 0)
                .forEach(entry -> emitEventToClient(sseEmitter, entry.getKey(), entry.getValue()));
    }

    /**
     * 클라이언트로 이벤트 전송
     */
    @Override
/**
 * 클라이언트로 이벤트 전송
 */
    public void emitEventToClient(SseEmitter sseEmitter, String emitterId, Notification notification) {
        try {
            // 클라이언트로 데이터 전송
            sseEmitter.send(SseEmitter.event()
                    .id(emitterId)
                    .name("notification")
                    .data(notification, MediaType.APPLICATION_JSON));
            log.info("Notification sent to client: {}", emitterId);
        } catch (IOException e) {
            // 전송 실패 시 Emitter 제거
            notificationRepository.deleteEmitterById(emitterId);
            log.error("Failed to emit SSE event. Emitter removed: {}", emitterId, e);
        }
    }


    /**
     * 사용자별 이벤트 전송
     */
    public void sendEvent(String userName, String eventContent) {
        log.info("sendEvent called for user: {}, with content: {}", userName, eventContent);

        List<SseEmitter> emitters = notificationRepository.findAllEmittersStartsWithUsername(userName);

        if (!emitters.isEmpty()) {
            emitters.forEach(emitter -> {
                try {
                    emitter.send(SseEmitter.event()
                            .name("notification")
                            .data(eventContent));
                    log.info("Event successfully sent to user: {}", userName);
                } catch (IOException e) {
                    log.error("Failed to send event to user: {}", userName, e);
                    notificationRepository.deleteEmitterById(userName);
                }
            });
        } else {
            log.warn("No emitters found for user: {}", userName);
        }
    }

    /**
     * Emitter 종료 핸들러
     */
     void handleEmitterCompletion(String emitterId) {
        notificationRepository.deleteEmitterById(emitterId);
        log.info("SSE connection completed: {}", emitterId);
    }

    /**
     * Emitter 타임아웃 핸들러
     */
     void handleEmitterTimeout(String emitterId) {
        notificationRepository.deleteEmitterById(emitterId);
        log.warn("SSE connection timed out: {}", emitterId);
    }

    /**
     * Emitter 오류 핸들러
     */
     void handleEmitterError(String emitterId, Throwable error) {
        notificationRepository.deleteEmitterById(emitterId);
        log.error("SSE connection error: {}", emitterId, error);
    }
}