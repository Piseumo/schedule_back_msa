package com.example.calendarService.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Builder
@Getter
public class DiaryResponseMonthDto {

    private String title;

    private LocalDate date;

}
