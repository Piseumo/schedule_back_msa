package com.example.calendarservice.dto.response;

import com.example.calendarservice.constant.Color;
import com.example.calendarservice.constant.Share;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
public class ScheduleResponseMonthDto {

    private Long idx;

    private String title;

    private LocalDateTime start;

    private LocalDateTime end;

    private Color color;

    private Share share;

}
