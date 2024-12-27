package com.example.calendarservice.dto.request;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CommentsRequestInsertDto {

    private Long userIdx;
    private Long scheduleIdx;
    private Long diaryIdx;
    private LocalDateTime dateTime;
    private String content;
}
