package com.example.notificationService.repository;


import com.example.notificationService.entity.Notification;
import feign.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;
import java.util.Map;

public interface NotificationRepository extends JpaRepository<Notification, Long> {


    // 1. SSE Emitter 저장
    @Query("SELECT s FROM Notification n WHERE n.notificationId = :emitterId")
    SseEmitter findEmitterById(@Param("emitterId") String emitterId);

    // 2. SSE 이벤트 캐시 저장 - 캐시를 저장할 데이터베이스 또는 서비스 구현 필요
    void saveEventCache(String eventCacheId, Object event);

    // 3. Receiver로 시작하는 모든 Emitters 찾기
    @Query("SELECT n FROM Notification n WHERE n.receiver LIKE CONCAT(:username, '%')")
    List<Notification> findAllEmitterStartsWithUsername(@Param("username") String username);

    // 4. Receiver로 시작하는 모든 이벤트 캐시 찾기
    @Query("SELECT n FROM Notification n WHERE n.receiver LIKE CONCAT(:username, '%')")
    List<Object> findAllEventCacheStartsWithUsername(@Param("username") String username);

    // 5. 특정 Emitter 삭제
    @Query("DELETE FROM Notification n WHERE n.notificationId = :emitterId")
    void deleteEmitterById(@Param("emitterId") String id);

    // 6. 특정 ID로 시작하는 모든 Emitters 삭제
    @Query("DELETE FROM Notification n WHERE n.notificationId LIKE CONCAT(:id, '%')")
    void deleteAllEmitterStartsWithId(@Param("id") String id);

    // 7. 특정 ID로 시작하는 모든 이벤트 캐시 삭제
    @Query("DELETE FROM Notification n WHERE n.notificationId LIKE CONCAT(:id, '%')")
    void deleteAllEventCacheStartsWithId(@Param("id") String id);

    // 8. 특정 Event Cache 삭제
    @Query("DELETE FROM Notification n WHERE n.notificationId = :eventCacheId")
    void deleteEventCacheById(@Param("eventCacheId") String emitterId);
}
