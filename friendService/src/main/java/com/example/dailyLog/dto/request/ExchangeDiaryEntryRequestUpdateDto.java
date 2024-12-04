package com.example.dailyLog.dto.request;

import lombok.Data;

@Data
public class ExchangeDiaryEntryRequestUpdateDto {
    private Long entryId;
    private Long userId;
    private String title;
    private String content;
}
