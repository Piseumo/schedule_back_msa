package com.example.notificationService.repository;

import com.example.notificationService.entity.Notification;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
@Slf4j
public class NotificationRepository {

    // Emitter 관리 맵
    private final Map<String, SseEmitter> sseEmitters = new ConcurrentHashMap<>();

    // 이벤트 캐시 관리 맵
    private final Map<String, Object> eventCache = new ConcurrentHashMap<>();

    /**
     * Emitter 저장
     */
    public SseEmitter subscribeSave(String emitterId, SseEmitter sseEmitter) {
        sseEmitters.put(emitterId, sseEmitter);
        log.info("SSE Emitter 등록: {}", emitterId);
        return sseEmitter;
    }

    /**
     * 특정 Emitter 검색
     */
    public SseEmitter findEmitterById(String emitterId) {
        return sseEmitters.get(emitterId);
    }

    /**
     * 특정 Emitter 삭제
     */
    public void deleteEmitterById(String emitterId) {
        sseEmitters.remove(emitterId);
        eventCache.remove(emitterId);
        log.info("Emitter 제거: {}", emitterId);
    }

    /**
     * 특정 유저의 모든 Emitter 조회
     */
    public List<Notification> findAllEmitterStartsWithUsername(String username) {
        List<Notification> notifications = new ArrayList<>();
        sseEmitters.forEach((key, emitter) -> {
            if (key.startsWith(username)) {
                Notification notification = (Notification) eventCache.get(key);
                if (notification != null) {
                    notifications.add(notification);
                }
            }
        });
        return notifications;
    }

    /**
     * 특정 유저의 모든 이벤트 캐시 조회
     */
    public Map<String, Object> findAllEventCacheStartsWithUsername(String username) {
        Map<String, Object> userEventCache = new HashMap<>();
        eventCache.forEach((key, value) -> {
            if (key.startsWith(username)) {
                userEventCache.put(key, value);
            }
        });
        return userEventCache;
    }

    /**
     * 이벤트 캐시 저장
     */
    public void saveEventCache(String key, Object event) {
        eventCache.put(key, event);
        log.info("이벤트 캐시 저장: {}", key);
    }
}
