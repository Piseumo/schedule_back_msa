package com.example.calendarservice.dto.request;

import jakarta.persistence.Column;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Builder
@Data
public class SharedRequestInsertDto {

    private Long scheduleIdx;

    private Long diaryIdx;

    private Long friendIdx;

    private LocalDateTime shareDateTime;
}
