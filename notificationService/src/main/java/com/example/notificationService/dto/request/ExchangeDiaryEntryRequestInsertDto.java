package com.example.friendservice.dto.request;

import lombok.Data;

@Data
public class ExchangeDiaryEntryRequestInsertDto {
    private Long diaryId;
    private Long userId;
    private String title;
    private String content;
}
