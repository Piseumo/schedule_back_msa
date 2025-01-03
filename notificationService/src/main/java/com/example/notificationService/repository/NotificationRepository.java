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
    private final Map<String, Notification> eventCache = new ConcurrentHashMap<>();

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
        SseEmitter emitter = sseEmitters.get(emitterId);
        if (emitter != null) {
            log.info("Emitter 조회 성공: {}", emitterId);
        } else {
            log.warn("Emitter 조회 실패: {}", emitterId);
        }
        return emitter;
    }

    /**
     * 특정 유저의 모든 Emitter 조회
     */
    public List<SseEmitter> findAllEmittersStartsWithUsername(String username) {
        List<SseEmitter> emitters = new ArrayList<>();
        sseEmitters.forEach((key, emitter) -> {
            if (key.startsWith(username)) {
                emitters.add(emitter);
            }
        });
        log.info("사용자 '{}'의 모든 Emitter 조회 완료. 총 {}개", username, emitters.size());
        return emitters;
    }

    /**
     * 특정 Emitter 삭제
     */
    public void deleteEmitterById(String emitterId) {
        sseEmitters.remove(emitterId);
        eventCache.remove(emitterId);
        log.info("Emitter 및 이벤트 캐시 제거: {}", emitterId);
    }

    /**
     * 특정 유저의 모든 Notification 조회
     */
    public List<Notification> findAllNotificationsByUsername(String username) {
        List<Notification> notifications = new ArrayList<>();
        eventCache.forEach((key, notification) -> {
            if (key.startsWith(username)) {
                notifications.add(notification);
            }
        });
        log.info("사용자 '{}'의 모든 Notification 조회 완료. 총 {}개", username, notifications.size());
        return notifications;
    }

    /**
     * 이벤트 캐시 저장
     */
    public void saveEventCache(String key, Notification notification) {
        eventCache.put(key, notification);
        log.info("Notification 이벤트 캐시 저장: {}", key);
    }

    /**
     * 특정 유저의 모든 이벤트 캐시 조회
     */
    public Map<String, Notification> findAllEventCacheStartsWithUsername(String username) {
        Map<String, Notification> userEventCache = new HashMap<>();
        eventCache.forEach((key, value) -> {
            if (key.startsWith(username)) {
                userEventCache.put(key, value);
            }
        });
        log.info("사용자 '{}'의 모든 이벤트 캐시 조회 완료. 총 {}개", username, userEventCache.size());
        return userEventCache;
    }
}