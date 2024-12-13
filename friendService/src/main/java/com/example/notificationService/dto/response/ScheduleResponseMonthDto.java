package com.example.notificationService.dto.response;

import com.example.notificationService.constant.Color;
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
