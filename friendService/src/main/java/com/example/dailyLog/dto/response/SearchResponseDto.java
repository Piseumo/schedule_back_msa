package com.example.dailyLog.dto.response;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SearchResponseDto {

    private Long idx;
    private String title;
    private String content;
    private LocalDateTime startDate;
    private LocalDate date;
    private String type;
    private String category;

}
