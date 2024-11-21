package com.example.calendarService.dto.response;

import com.example.calendarService.constant.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StatisticsCategoryDto {

    private Category category;
    private Long count;
    private Double percentage;

}
