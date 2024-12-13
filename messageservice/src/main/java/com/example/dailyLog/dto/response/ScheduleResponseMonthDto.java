package com.example.dailyLog.dto.response;

import com.example.dailyLog.constant.Color;
import com.example.dailyLog.entity.Calendars;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
public class ScheduleResponseMonthDto {


    private String title;

    private LocalDateTime start;

    private Color color;

}
