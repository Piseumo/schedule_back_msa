package com.example.calendarService.service;

import com.example.calendarService.dto.request.ScheduleRequestInsertDto;
import com.example.calendarService.dto.request.ScheduleRequestUpdateDto;
import com.example.calendarService.dto.response.ScheduleResponseDayDto;
import com.example.calendarService.dto.response.ScheduleResponseMonthDto;
import com.example.calendarService.dto.response.ScheduleResponseYearDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ScheduleService {

    List<ScheduleResponseYearDto> findAllYearSchedule(Long calendarIdx, int year);
    List<ScheduleResponseMonthDto> findAllMonthSchedule(Long calendarIdx, int year, int month);
    List<ScheduleResponseDayDto> findScheduleByDay(Long calendarIdx, int year, int month, int day);
    ScheduleResponseDayDto findScheduleByOne(Long scheduleIdx);

    void saveSchedule(ScheduleRequestInsertDto scheduleRequestInsertDto, List<MultipartFile> imageFileList);
    void updateSchedule(ScheduleRequestUpdateDto scheduleRequestUpdateDto,List<MultipartFile> imageFileList);
    void deleteSchedule(Long scheduleId, boolean deleteAllRepeats, boolean deleteOnlyThis, boolean deleteAfter);
}