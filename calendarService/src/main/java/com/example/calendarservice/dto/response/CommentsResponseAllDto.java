package com.example.calendarservice.dto.response;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentsResponseAllDto {

    private Long commentsIdx;

    private Long userIdx;

    private Long sharedIdx;

    private LocalDateTime dateTime;

    private String content;
}
