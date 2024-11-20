package com.example.dailyLog.dto.response;

import com.example.dailyLog.entity.Calendars;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Builder
@Getter
public class DiaryResponseMonthDto {

    private String title;

    private LocalDate date;

}
