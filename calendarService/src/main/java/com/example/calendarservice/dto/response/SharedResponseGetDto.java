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
public class SharedResponseGetDto {

    private Long sharedIdx;

    private Long scheduleIdx;

    private Long diaryIdx;

    private Long friendIdx;

    private LocalDateTime dateTime;
}
