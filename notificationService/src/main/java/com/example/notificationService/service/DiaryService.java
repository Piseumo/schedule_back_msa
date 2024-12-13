package com.example.notificationService.service;

import com.example.notificationService.dto.request.DiaryRequestInsertDto;
import com.example.notificationService.dto.request.DiaryRequestUpdateDto;
import com.example.notificationService.dto.response.DiaryResponseCategoryDto;
import com.example.notificationService.dto.response.DiaryResponseDayDto;
import com.example.notificationService.dto.response.DiaryResponseDayListDto;
import com.example.notificationService.dto.response.DiaryResponseMonthDto;
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
