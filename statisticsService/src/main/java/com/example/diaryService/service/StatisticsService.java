package com.example.diaryService.service;

import com.example.diaryService.dto.StatisticsCategoryDto;
import com.example.diaryService.dto.StatisticsMonthDto;
import com.example.diaryService.dto.StatisticsYearDto;

import java.util.List;


public interface StatisticsService {

    List<StatisticsCategoryDto> getCategoryStatisticsByUser(Long idx);
    public List<StatisticsMonthDto> getMonthStatistics(Long userIdx, int year, int month);
    public List<StatisticsYearDto> getYearStatistics(Long userIdx, int year);
}
