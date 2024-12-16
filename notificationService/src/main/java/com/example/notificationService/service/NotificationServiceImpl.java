package com.example.friendservice.service;

import com.example.friendservice.constant.NotificationType;
import com.example.friendservice.entity.Notification;
import com.example.friendservice.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.lang.reflect.Member;
import java.util.Map;

import static org.springframework.retry.policy.TimeoutRetryPolicy.DEFAULT_TIMEOUT;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationServiceImpl implements NotificationService {

    private  final NotificationRepository notificationRepository;

    public SseEmitter subscribe(String member, String lastEventId) {
        String emitterId = member + "_" + System.currentTimeMillis();

        SseEmitter sseEmitter = notificationRepository.save(emitterId, new SseEmitter(DEFAULT_TIMEOUT));
        log.info("new emitter added : {}", sseEmitter);
        log.info("lastEventId : {}", lastEventId);

        /* 상황별 emitter 삭제 처리 */
        sseEmitter.onCompletion(() -> notificationRepository.deleteEmitterById(emitterId)); //완료 시, 타임아웃 시, 에러 발생 시
        sseEmitter.onTimeout(() -> notificationRepository.deleteEmitterById(emitterId));
        sseEmitter.onError((e) -> notificationRepository.deleteEmitterById(emitterId));

        /* 503 Service Unavailable 방지용 dummy event 전송 */
        send(sseEmitter, emitterId, createDummyNotification(member));

        /* client가 미수신한 event 목록이 존재하는 경우 */
        if(!lastEventId.isEmpty()) { //client가 미수신한 event가 존재하는 경우 이를 전송하여 유실 예방
            Map<String, Object> eventCaches = notificationRepository.findAllEventCacheStartsWithUsername(member); //id에 해당하는 eventCache 조회
            eventCaches.entrySet().stream() //미수신 상태인 event 목록 전송
                    .filter(entry -> lastEventId.compareTo(entry.getKey()) < 0)
                    .forEach(entry -> emitEventToClient(sseEmitter, entry.getKey(), entry.getValue()));
        }

        return sseEmitter;
    }

    /**
     * [SSE 통신]specific user에게 알림 전송
     */
    public void send(String receiver, String content, String type, String url) {
        Notification notification = createNotification(receiver, content, type, url);
        /* 로그인한 client의 sseEmitter 전체 호출 */
        Map<String, SseEmitter> sseEmitters = notificationRepository.findAllEmitterStartsWithUsername(receiver);
        sseEmitters.forEach(
                (key, sseEmitter) -> {
                    log.info("key, notification : {}, {}", key, notification);
                    notificationRepository.saveEventCache(key, notification); //저장
                    emitEventToClient(sseEmitter, key, notification); //전송
                }
        );
    }

    /**
     * [SSE 통신]dummy data 생성
     * : 503 Service Unavailable 방지
     */
    private Notification createDummyNotification(String receiver) {
        return Notification.builder()
                .notificationId(receiver + "_" + System.currentTimeMillis())
                .receiver(receiver)
                .content("send dummy data to client.")
                .notificationType(NotificationType.MESSAGE.getAlias())
                .url(NotificationType.MESSAGE.getPath())
                .readYn('N')
                .deletedYn('N')
                .build();
    }

    /**
     * [SSE 통신]notification type별 data 생성
     */
    private Notification createNotification(String receiver, String content, String type, String url) {
        if(type.equals(NotificationType.COMMENT.getAlias())) { //댓글
            return Notification.builder()
                    .receiver(receiver)
                    .content(content)
                    .notificationType(NotificationType.COMMENT.getAlias())
                    .url(url)
                    .readYn('N')
                    .deletedYn('N')
                    .build();
        } else if(type.equals(NotificationType.FRIEND_REQUEST.getAlias())) { //친구신청
            return Notification.builder()
                    .receiver(receiver)
                    .content(content)
                    .notificationType(NotificationType.FRIEND_REQUEST.getAlias())
                    .url(url)
                    .readYn('N')
                    .deletedYn('N')
                    .build();
        } else if(type.equals(NotificationType.FRIEND_ACCEPT.getAlias())) { //친구수락
            return Notification.builder()
                    .receiver(receiver)
                    .content(content)
                    .notificationType(NotificationType.FRIEND_ACCEPT.getAlias())
                    .url(url)
                    .readYn('N')
                    .deletedYn('N')
                    .build();
        } else if(type.equals(NotificationType.MESSAGE.getAlias())) { //쪽지
            return Notification.builder()
                    .receiver(receiver)
                    .content(content)
                    .notificationType(NotificationType.MESSAGE.getAlias())
                    .url(url)
                    .readYn('N')
                    .deletedYn('N')
                    .build();
        }
        else if(type.equals(NotificationType.FRIEND_NEW_POST.getAlias())) { //새글
            return Notification.builder()
                    .receiver(receiver)
                    .content(content)
                    .notificationType(NotificationType.FRIEND_NEW_POST.getAlias())
                    .url(url)
                    .readYn('N')
                    .deletedYn('N')
                    .build();
        }

        else {
            return null;
        }
    }

    /**
     * [SSE 통신]notification type별 event 전송
     */
    private void send(SseEmitter sseEmitter, String emitterId, Object data) {
        try {
            sseEmitter.send(SseEmitter.event()
                    .id(emitterId)
                    .name("sse")
                    .data(data, MediaType.APPLICATION_JSON));
        } catch(IOException exception) {
            notificationRepository.deleteEmitterById(emitterId);
            sseEmitter.completeWithError(exception);
        }
    }

    /**
     * [SSE 통신]
     */
    private void emitEventToClient(SseEmitter sseEmitter, String emitterId, Object data) {
        try {
            send(sseEmitter, emitterId, data);
            notificationRepository.deleteEmitterById(emitterId);
        } catch (Exception e) {
            notificationRepository.deleteEmitterById(emitterId);
            throw new RuntimeException("Connection Failed.");
        }
    }
}
