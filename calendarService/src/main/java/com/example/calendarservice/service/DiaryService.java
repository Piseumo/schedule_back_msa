package com.example.calendarservice.service;

import com.example.calendarservice.dto.request.DiaryRequestInsertDto;
import com.example.calendarservice.dto.request.DiaryRequestUpdateDto;
import com.example.calendarservice.dto.response.DiaryResponseCategoryDto;
import com.example.calendarservice.dto.response.DiaryResponseDayDto;
import com.example.calendarservice.dto.response.DiaryResponseDayListDto;
import com.example.calendarservice.dto.response.DiaryResponseMonthDto;
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
