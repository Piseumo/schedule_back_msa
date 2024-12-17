package com.example.calendarservice.dto.request;

import lombok.Data;


@Data
public class CommentsRequestUpdateDto {

    private Long commentsIdx;
    private String content;
}
