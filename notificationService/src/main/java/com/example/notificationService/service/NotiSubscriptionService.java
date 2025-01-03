package com.example.notificationService.service;

import com.example.notificationService.entity.Notification;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

public interface NotiSubscriptionService {
    SseEmitter subscribe(String username, String lastEventId);
    void emitEventToClient(SseEmitter sseEmitter, String emitterId, Notification notification);

    void sendEvent(String friendName, String eventContent);
}
