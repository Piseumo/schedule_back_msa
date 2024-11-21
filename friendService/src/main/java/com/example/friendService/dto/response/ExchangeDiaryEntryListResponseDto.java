package com.example.friendService.dto.response;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ExchangeDiaryEntryListResponseDto {
    private Long entryId;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private String authorName;

    // 생성자 정의
    public ExchangeDiaryEntryListResponseDto(Long entryId, String title, String content, LocalDateTime createdAt, String authorName) {
        this.entryId = entryId;
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
        this.authorName = authorName;
    }

    // Getters and Setters
}
