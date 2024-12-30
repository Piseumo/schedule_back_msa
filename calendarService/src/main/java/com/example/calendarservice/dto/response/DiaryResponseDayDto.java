package com.example.calendarservice.dto.response;

import com.example.calendarservice.constant.Category;
import com.example.calendarservice.constant.Share;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Builder
@Getter
public class DiaryResponseDayDto {

    private Long diaryIdx;

    private String title;

    private String content;

    private LocalDate date;

    private Category category;

    private Share share;

    private List<String> images;

    private List<Long> ids;

}
