package com.example.dailyLog.service;

import com.example.dailyLog.dto.request.DiaryRequestInsertDto;
import com.example.dailyLog.dto.request.DiaryRequestUpdateDto;
import com.example.dailyLog.dto.response.DiaryResponseCategoryDto;
import com.example.dailyLog.dto.response.DiaryResponseDayDto;
import com.example.dailyLog.dto.response.DiaryResponseDayListDto;
import com.example.dailyLog.dto.response.DiaryResponseMonthDto;
import com.example.dailyLog.entity.Calendars;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface DiaryService {

    List<DiaryResponseMonthDto> findAllMonthDiary(Long calendarIdx, int year, int month);
    List<DiaryResponseCategoryDto> findDiaryCategory(Long calendarIdx, String category);

    List<DiaryResponseDayListDto> findDiaryByDayList(Long calendarIdx, int year,int month, int day);
    DiaryResponseDayDto findDiaryByDay(Long idx);

    void saveDiary(DiaryRequestInsertDto diaryRequestInsertDto,List<MultipartFile> imageFileList);
    void updateDiary(DiaryRequestUpdateDto diaryRequestUpdateDto, List<MultipartFile> imageFileList);
    void deleteDiary(Long idx);
}
