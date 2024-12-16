package com.example.friendservice.repository;


import org.springframework.stereotype.Repository;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Repository
public class NotificationRepository {

    private final Map<String, SseEmitter> emitters = new ConcurrentHashMap<>();
    private final Map<String, Object> eventCache = new ConcurrentHashMap<>();

    public SseEmitter save(String emitterId, SseEmitter sseEmitter) {
        emitters.put(emitterId, sseEmitter);
        return sseEmitter;
    }

    public void saveEventCache(String eventCacheId, Object event) {
        eventCache.put(eventCacheId, event);
    }

    public Map<String, SseEmitter> findAllEmitterStartsWithUsername(String username) {
        return emitters.entrySet().stream()
                .filter(entry -> entry.getKey().startsWith(username))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public Map<String, Object> findAllEventCacheStartsWithUsername(String username) {
        return eventCache.entrySet().stream()
                .filter(entry -> entry.getKey().startsWith(username))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public void deleteEmitterById(String id) {
        emitters.remove(id);
    }

    public void deleteAllEmitterStartsWithId(String id) {
        emitters.keySet().removeIf(key -> key.startsWith(id));
    }

    public void deleteAllEventCacheStartsWithId(String id) {
        eventCache.keySet().removeIf(key -> key.startsWith(id));
    }
}
