package com.example.messageservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Builder
public class ConversationDto {
    private Long userId;
    private Long otherUserId;
    private String profileImage;
    private String userName;
    private String lastMessage;
    private LocalDateTime lastMessageTime;
}