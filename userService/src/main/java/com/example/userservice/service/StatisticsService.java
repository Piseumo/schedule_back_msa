package com.example.userservice.service;

import com.example.userservice.dto.response.StatisticsCategoryDto;
import com.example.userservice.dto.response.StatisticsMonthDto;
import com.example.userservice.dto.response.StatisticsYearDto;

import java.util.List;


public interface StatisticsService {

    List<StatisticsCategoryDto> getCategoryStatisticsByUser(Long idx);
    public List<StatisticsMonthDto> getMonthStatistics(Long userIdx, int year, int month);
    public List<StatisticsYearDto> getYearStatistics(Long userIdx, int year);
}
