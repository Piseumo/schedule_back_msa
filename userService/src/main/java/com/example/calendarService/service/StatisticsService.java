package com.example.calendarService.service;

import com.example.calendarService.dto.response.StatisticsCategoryDto;
import com.example.calendarService.dto.response.StatisticsMonthDto;
import com.example.calendarService.dto.response.StatisticsYearDto;

import java.util.List;


public interface StatisticsService {

    List<StatisticsCategoryDto> getCategoryStatisticsByUser(Long idx);
    public List<StatisticsMonthDto> getMonthStatistics(Long userIdx, int year, int month);
    public List<StatisticsYearDto> getYearStatistics(Long userIdx, int year);
}
