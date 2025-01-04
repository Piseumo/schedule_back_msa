package com.example.messageservice.controller;

import com.example.messageservice.dto.request.MessageRequestDto;
import com.example.messageservice.dto.response.ConversationDto;
import com.example.messageservice.entity.Message;
import com.example.messageservice.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequestMapping("message")
@RequiredArgsConstructor
public class MessageController {
    private final MessageService messageService;
    private final ConcurrentHashMap<Long, SseEmitter> emitters = new ConcurrentHashMap<>();

    // SSE 연결 설정
    @GetMapping("/subscribe/{userId}")
    public SseEmitter subscribe(@PathVariable Long userId) {
        SseEmitter emitter = new SseEmitter(Long.MAX_VALUE);
        emitters.put(userId, emitter);

        emitter.onCompletion(() -> emitters.remove(userId));
        emitter.onTimeout(() -> emitters.remove(userId));
        emitter.onError(e -> emitters.remove(userId));

        return emitter;
    }

    // 메시지 전송
    @PostMapping("/send")
    public ResponseEntity<Message> sendMessage(@RequestBody MessageRequestDto requestDto) {
        Message message = messageService.sendMessage(requestDto);

        // 실시간으로 메시지 전송
        SseEmitter emitter = emitters.get(message.getReceiverId());
        if (emitter != null) {
            try {
                emitter.send(SseEmitter.event()
                        .name("message")
                        .data(message));
            } catch (IOException e) {
                emitters.remove(message.getReceiverId());
            }
        }

        return ResponseEntity.ok(message);
    }

    @GetMapping("/messages/{userId}/{otherUserId}")
    public ResponseEntity<List<Message>> getMessages(
            @PathVariable Long userId,
            @PathVariable Long otherUserId
    ) {
        List<Message> messages = messageService.getMessages(userId, otherUserId);
        return ResponseEntity.ok(messages);
    }

    @GetMapping("/conversations/{userId}")
    public ResponseEntity<List<ConversationDto>> getRecentConversations(@PathVariable(name = "userId") Long userId) {
        List<ConversationDto> conversations = messageService.getRecentConversations(userId);
        return ResponseEntity.ok(conversations);
    }

    @DeleteMapping("/{userId}/{friendId}")
    public ResponseEntity<String> deleteMessage(@PathVariable Long userId,
                                                           @PathVariable Long friendId) {
        messageService.deleteMessage(userId, friendId);
        return ResponseEntity.ok("All message deleted.");
    }
}