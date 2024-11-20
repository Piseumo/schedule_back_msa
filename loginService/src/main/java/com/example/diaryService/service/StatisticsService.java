package com.example.diaryService.service;

import com.example.diaryService.dto.response.StatisticsCategoryDto;
import com.example.diaryService.dto.response.StatisticsMonthDto;
import com.example.diaryService.dto.response.StatisticsYearDto;

import java.util.List;


public interface StatisticsService {

    List<StatisticsCategoryDto> getCategoryStatisticsByUser(Long idx);
    public List<StatisticsMonthDto> getMonthStatistics(Long userIdx, int year, int month);
    public List<StatisticsYearDto> getYearStatistics(Long userIdx, int year);
}
