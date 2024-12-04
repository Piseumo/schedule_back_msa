package com.example.calendarservice.service;

import com.example.calendarservice.dto.response.StatisticsCategoryDto;
import com.example.calendarservice.dto.response.StatisticsMonthDto;
import com.example.calendarservice.dto.response.StatisticsYearDto;

import java.util.List;


public interface StatisticsService {

    List<StatisticsCategoryDto> getCategoryStatisticsByUser(Long idx);
    public List<StatisticsMonthDto> getMonthStatistics(Long userIdx, int year, int month);
    public List<StatisticsYearDto> getYearStatistics(Long userIdx, int year);
}
