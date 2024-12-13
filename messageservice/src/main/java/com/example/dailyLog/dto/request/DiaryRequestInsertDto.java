package com.example.dailyLog.dto.request;

import com.example.dailyLog.constant.Category;
import com.example.dailyLog.entity.Calendars;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
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