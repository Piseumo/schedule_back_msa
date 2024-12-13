package com.example.dailyLog.dto.response;

import com.example.dailyLog.constant.Category;
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
