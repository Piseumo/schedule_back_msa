package com.example.calendarservice.dto.request;

import com.example.calendarservice.constant.Category;
import com.example.calendarservice.constant.Share;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDate;
import java.util.List;

@Data
public class DiaryRequestInsertDto {

    @Length(min = 1, max = 50)
    private String title;

    @Length(max = 3000)
    private String content;

    private LocalDate date;

    private Category category;

    private Share share;

    @Schema(example = "c_idx")
    private Long calendarIdx;

    private List<Long> friendIdxList;
}