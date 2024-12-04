package com.example.userservice.dto.request;

import com.example.userservice.constant.Category;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDate;

@Data
public class DiaryRequestInsertDto {

    @Length(min = 1, max = 50)
    private String title;

    @Length(max = 3000)
    private String content;

    private LocalDate date;

    private Category category;

    @Schema(example = "c_idx", hidden = true)
    private Long calendarIdx;
}