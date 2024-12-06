package com.example.friendservice.service;

import com.example.friendservice.dto.response.StatisticsCategoryDto;
import com.example.friendservice.dto.response.StatisticsMonthDto;
import com.example.friendservice.dto.response.StatisticsYearDto;

import java.util.List;


public interface StatisticsService {

    List<StatisticsCategoryDto> getCategoryStatisticsByUser(Long idx);
    public List<StatisticsMonthDto> getMonthStatistics(Long userIdx, int year, int month);
    public List<StatisticsYearDto> getYearStatistics(Long userIdx, int year);
}
