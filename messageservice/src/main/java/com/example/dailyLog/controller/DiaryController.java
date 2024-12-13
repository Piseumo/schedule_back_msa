package com.example.dailyLog.controller;

import com.example.dailyLog.dto.request.DiaryRequestInsertDto;
import com.example.dailyLog.dto.request.DiaryRequestUpdateDto;
import com.example.dailyLog.dto.request.ScheduleRequestInsertDto;
import com.example.dailyLog.dto.request.ScheduleRequestUpdateDto;
import com.example.dailyLog.dto.response.*;
import com.example.dailyLog.entity.Calendars;
import com.example.dailyLog.exception.calendarsException.CalendarsErrorCode;
import com.example.dailyLog.exception.calendarsException.CalendarsNotFoundException;
import com.example.dailyLog.repository.CalendarRepository;
import com.example.dailyLog.service.DiaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/diary")
@RequiredArgsConstructor
public class DiaryController {

    private final DiaryService diaryService;

    // 홈페이지 첫화면 기본 창(월달력 조회)
    @GetMapping("/{calendarIdx}/{year}/{month}")
    public ResponseEntity<List<DiaryResponseMonthDto>> getAllMonthDiary(
            @PathVariable(name = "calendarIdx") Long calendarIdx,
            @PathVariable(name = "year") int year,
            @PathVariable(name = "month") int month) {

        List<DiaryResponseMonthDto> diaryResponseMonthDto = diaryService.findAllMonthDiary(calendarIdx, year, month);
        return ResponseEntity.ok(diaryResponseMonthDto);
    }

    //해당 유저의 카테고리별 다이어리 조회
    @GetMapping("/{calendarIdx}/{category}")
    public ResponseEntity<List<DiaryResponseCategoryDto>> getCategoryDiary(
            @PathVariable(name = "calendarIdx") Long calendarIdx,
            @PathVariable(name = "category") String category) {

        List<DiaryResponseCategoryDto> diaryResponseCategoryDto = diaryService.findDiaryCategory(calendarIdx, category);
        return ResponseEntity.ok(diaryResponseCategoryDto);
    }

    // 핑크창?
    @GetMapping("/{calendarIdx}/{year}/{month}/{day}")
    public ResponseEntity<List<DiaryResponseDayListDto>> getDayListDiary(
            @PathVariable(name = "calendarIdx") Long calendarIdx,
            @PathVariable(name = "year") int year,
            @PathVariable(name = "month") int month,
            @PathVariable(name = "day") int day) {

        List<DiaryResponseDayListDto> diaryResponseDayListDto = diaryService.findDiaryByDayList(calendarIdx, year, month, day);
        return ResponseEntity.ok(diaryResponseDayListDto);
    }

    // 특정 일기 전체내용 조회
    @GetMapping("/{idx}")
    public ResponseEntity<DiaryResponseDayDto> getDiary(
            @PathVariable(name = "idx") Long idx) {
        DiaryResponseDayDto diaryResponseDayDto = diaryService.findDiaryByDay(idx);
        return ResponseEntity.ok(diaryResponseDayDto);
    }


    // 일기 입력
    @PostMapping(value = "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> saveDiary(@RequestPart(name = "diaryRequest") DiaryRequestInsertDto diaryRequestInsertDto,
                                            @RequestPart(name = "imageFiles", required = false) List<MultipartFile> imageFileList) {
        diaryService.saveDiary(diaryRequestInsertDto, imageFileList);

        return ResponseEntity.status(HttpStatus.CREATED).body("Diary created successfully");

    }


    // 일기 수정
    @PostMapping(value = "/update", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> updateDiary(@RequestPart(name = "diaryRequest") DiaryRequestUpdateDto diaryRequestUpdateDto,
                                              @RequestPart(name = "imageFiles", required = false) List<MultipartFile> imageFileList) {
        diaryService.updateDiary(diaryRequestUpdateDto, imageFileList);
        return ResponseEntity.status(HttpStatus.OK).body("Diary updated successfully");
    }


    // 일기 삭제
    @DeleteMapping(value = "/delete/{idx}")
    public ResponseEntity<String> deleteDiary(@PathVariable(name = "idx") Long idx) {
        diaryService.deleteDiary(idx);
        return ResponseEntity.status(HttpStatus.OK).body("Diary deleted successfully");
    }
}