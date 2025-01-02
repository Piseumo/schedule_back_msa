package com.example.notificationService.controller;

import com.example.notificationService.service.NotiSubscriptionService;
import com.example.notificationService.service.NotificationService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
@RequestMapping("/noti")
@RequiredArgsConstructor
public class NotificationController {

    private final NotiSubscriptionService notiSubscriptionService;
    private final NotificationService notificationService;

    @Operation(summary = "SSE 세션 연결")
    @GetMapping(value = "/api/subscribe", produces = "text/event-stream")
    public ResponseEntity<SseEmitter> subscribe(
            @RequestParam("userName") String userName,
            @RequestHeader(value = "Last-Event-ID", required = false, defaultValue = "") String lastEventId) {
        try {
            SseEmitter emitter = notiSubscriptionService.subscribe(userName, lastEventId);
            return ResponseEntity.ok(emitter);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(null);
        }
    }

    @Operation(summary = "친구 신청 알림")
    @PostMapping(value = "/friend-request")
    public ResponseEntity<String> friendRequest(@RequestParam String userName, @RequestParam String friendName) {
        try {
            String message = notificationService.sendFriendRequest(userName, friendName);
            notiSubscriptionService.sendEvent(friendName, userName + "님이 친구 요청을 보냈습니다.");
            return ResponseEntity.ok("친구 요청이 전송되었습니다.");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("친구 요청 전송 실패");
        }
    }

    @Operation(summary = "친구 수락 알림")
    @PostMapping(value = "/friend-accept")
    public ResponseEntity<String> friendAccept(@RequestParam String userName, @RequestParam String friendName) {
        try {
            notificationService.sendFriendAccept(userName, friendName);
            notiSubscriptionService.sendEvent(friendName, userName + "님이 친구 요청을 수락했습니다.");
            return ResponseEntity.ok("친구 요청을 수락했습니다.");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("친구 요청 수락 실패");
        }
    }
}

//    @Operation(summary = "쪽지 알림")
//    @GetMapping(value = "/message")
//    public ResponseEntity<Void> messageNotification(@AuthenticationPrincipal UserDetails userDetails) {
//        notificationService.sendMessage(userDetails.getUsername());
//        return ResponseEntity.ok().build();
//    }
//
//    @Operation(summary = "댓글 알림")
//    @GetMapping(value = "/comment")
//    public ResponseEntity<Void> commentNotification(@AuthenticationPrincipal UserDetails userDetails) {
//        notificationService.sendComment(userDetails.getUsername());
//        return ResponseEntity.ok().build();
//    }
//
//    @Operation(summary = "친구 새 글 알림")
//    @GetMapping(value = "/friend-post")
//    public ResponseEntity<Void> friendPostNotification(@AuthenticationPrincipal UserDetails userDetails) {
//        notificationService.sendFriendPost(userDetails.getUsername());
//        return ResponseEntity.ok().build();
//    }


// 다섯개 알람 각각 어떻게 뭘받고 뭘 보낼지 정하고 쌤한테 확인 받기

//    친구 신청 : 1번님이 userId님에게 친구 신청을 하였습니다<수락, 거절>
//- 보낸사람(1번), userId


//
//친구 수락: userid 님이 2번님에게 보낸 친구신청이 수락 되었습니다.
//- userid, 받은사람(2번)
//
//쪽지: 빨간점 유무, 그 안에 몇통 왓는지 숫자로 갯수 표현 만 있으면 됨
//userid, 읽지 않은 받은 쪽지 와 그 갯수?
//
//댓글: 내가 작성한 일정과 일기에 달릴 댓글들
//calid -> 작성 글(s_idx, d_idx )에 대한 권한 (전체, 선택친구 인것만) -> 그 중에서도 댓글이 insert 되었을때만
//
//선택 친구 새글: oo님이 일정 or 일기를 userId님에게 공유하였습니다.
//[userid 랑 oo님 id -> oo의 cal id -> s d idx -> userid를 선택 했을때 ]
//    ㄴ 아마도 이걸 한방에 객체로 보낼듯


