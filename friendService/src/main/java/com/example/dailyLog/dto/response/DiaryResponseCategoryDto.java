package com.example.dailyLog.dto.response;

import com.example.dailyLog.constant.Category;
import com.example.dailyLog.entity.Calendars;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Builder
@Getter
public class DiaryResponseCategoryDto {

    private Long idx;

    private String title;

    private LocalDate date;

    private Category category;

}
