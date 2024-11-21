package com.example.calendarService.dto.response;

import com.example.calendarService.constant.Color;
import com.example.calendarService.constant.RepeatType;
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
