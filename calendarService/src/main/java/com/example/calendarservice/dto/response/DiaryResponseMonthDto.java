package com.example.calendarservice.dto.response;

import com.example.calendarservice.constant.Share;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Builder
@Getter
public class DiaryResponseMonthDto {

    private String title;

    private LocalDate date;

    private Share share;

}
