package com.example.dailyLog.dto.response;

import com.example.dailyLog.constant.Color;
import com.example.dailyLog.constant.RepeatType;
import com.example.dailyLog.entity.Calendars;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Builder
@Getter
public class ScheduleResponseDayDto {

    private Long idx;

    private String title;

    private String content;

    private LocalDateTime start;

    private LocalDateTime end;

    private String location;

    private Color color;

    private List<String> images;

    private RepeatType repeatType;

    private LocalDate repeatEndDate;

}
