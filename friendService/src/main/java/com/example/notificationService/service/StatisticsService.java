package com.example.notificationService.service;

import com.example.notificationService.dto.response.StatisticsCategoryDto;
import com.example.notificationService.dto.response.StatisticsMonthDto;
import com.example.notificationService.dto.response.StatisticsYearDto;

import java.util.List;


public interface StatisticsService {

    List<StatisticsCategoryDto> getCategoryStatisticsByUser(Long idx);
    public List<StatisticsMonthDto> getMonthStatistics(Long userIdx, int year, int month);
    public List<StatisticsYearDto> getYearStatistics(Long userIdx, int year);
}
