package com.example.dailyLog.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ConversationDto {
    private Long userId;
    private String profileImage;
    private String nickname;
    private String lastMessage;
    private LocalDateTime lastMessageTime;
}