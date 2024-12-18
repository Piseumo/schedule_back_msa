package com.example.calendarservice.dto.request;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;


@Data
@Builder
public class SharedRequestUpdateDto {

    private Long sharedIdx;

    private Long scheduleIdx;

    private Long diaryIdx;

    private Long friendIdx;

    private LocalDateTime shareDateTime;
}
