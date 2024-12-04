package com.example.userservice.dto.response;

import com.example.userservice.constant.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StatisticsMonthDto {

    private String date;
    private Long diaryCount;
    private Long cumulativeDiaryCount;
    private Double totalPercentage;
    private Map<Category, Double> CategoryPercentageMap;
}
