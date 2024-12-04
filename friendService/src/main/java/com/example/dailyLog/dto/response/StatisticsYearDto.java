package com.example.dailyLog.dto.response;

import com.example.dailyLog.constant.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StatisticsYearDto {

    private String year; // "2024년 1월"과 같은 형식
    private Long totalDiaryCount; // 해당 월의 총 일기 작성 횟수
    private Map<Category, Double> categoryPercentageMap; // 해당 월의 카테고리별 비율
    private Long cumulativeDiaryCount; // 누적 일기 작성 횟수
}
