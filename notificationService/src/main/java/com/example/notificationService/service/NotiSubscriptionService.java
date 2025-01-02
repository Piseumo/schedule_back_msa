package com.example.notificationService.service;

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

public interface NotiSubscriptionService {
    SseEmitter subscribe(String username, String lastEventId);

    void sendEvent(String friendName, String eventContent);
}
