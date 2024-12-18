package com.example.notificationService.service;

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

        @Override
        public SseEmitter subscribe(String username, String lastEventId) {
            String emitterId = username + "_" + System.currentTimeMillis();
            SseEmitter sseEmitter = notificationRepository.subscribeSave(emitterId, new SseEmitter(Long.MAX_VALUE));

            log.info("New SSE connection established: {}", emitterId);

            // SSE 이벤트 종료 핸들러
            sseEmitter.onCompletion(() -> notificationRepository.deleteEmitterById(emitterId));
            sseEmitter.onTimeout(() -> notificationRepository.deleteEmitterById(emitterId));
            sseEmitter.onError((e) -> notificationRepository.deleteEmitterById(emitterId));

            // 연결 유지용 더미 알림 전송
            sendDummyNotification(username, emitterId);

            // 이전 이벤트 캐시 재전송
            if (!lastEventId.isEmpty()) {
                Map<String, Object> eventCaches = notificationRepository.findAllEventCacheStartsWithUsername(username);
                eventCaches.entrySet().stream()
                        .filter(entry -> lastEventId.compareTo(entry.getKey()) < 0)
                        .forEach(entry -> emitEventToClient(sseEmitter, entry.getKey(), entry.getValue()));
            }

            return sseEmitter;
        }

        private void sendDummyNotification(String username, String emitterId) {
            Notification dummy = Notification.builder()
                    .notificationId(emitterId)
                    .receiver(username)
                    .content("Connection Established")
                    .notificationType("SYSTEM")
                    .url("/system")
                    .readYn('N')
                    .deletedYn('N')
                    .build();
            emitEventToClient(notificationRepository.findEmitterById(emitterId), emitterId, dummy);
        }

        private void emitEventToClient(SseEmitter sseEmitter, String emitterId, Object data) {
            try {
                sseEmitter.send(SseEmitter.event()
                        .id(emitterId)
                        .name("sse")
                        .data(data, MediaType.APPLICATION_JSON));
            } catch (IOException e) {
                notificationRepository.deleteEmitterById(emitterId);
                throw new RuntimeException("SSE connection failed.", e);
            }
        }
    }
