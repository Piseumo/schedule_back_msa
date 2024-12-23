package com.example.calendarservice.controller;

import com.example.calendarservice.dto.request.ScheduleRequestInsertDto;
import com.example.calendarservice.dto.request.ScheduleRequestUpdateDto;
import com.example.calendarservice.dto.response.ScheduleResponseDayDto;
import com.example.calendarservice.dto.response.ScheduleResponseMonthDto;
import com.example.calendarservice.dto.response.ScheduleResponseYearDto;
import com.example.calendarservice.feign.KakaoMessageClient;
import com.example.calendarservice.service.ScheduleService;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;
import java.util.Enumeration;
import java.util.List;


@RestController
@RequestMapping("/schedule")
@RequiredArgsConstructor
@CrossOrigin
public class ScheduleController {

    private final ScheduleService scheduleService;

    private final KakaoMessageClient kakaoMessageClient;


    // 홈페이지 첫화면 기본 창(월달력 조회)
    @GetMapping("/{calendarIdx}/{year}/{month}")
    public ResponseEntity<List<ScheduleResponseMonthDto>> getAllMonthSchedule(
            @PathVariable(name = "calendarIdx") Long calendarIdx,
            @PathVariable(name = "year") int year,
            @PathVariable(name = "month") int month) {

        List<ScheduleResponseMonthDto> scheduleResponseMonthDto = scheduleService.findAllMonthSchedule(calendarIdx, year, month);
        return ResponseEntity.ok(scheduleResponseMonthDto);
    }

    // 연달력 전체 일정 조회
    @GetMapping("/{calendarIdx}/{year}")
    public ResponseEntity<List<ScheduleResponseYearDto>> getAllYearSchedule(
            @PathVariable(name = "calendarIdx") Long calendarIdx,
            @PathVariable(name = "year") int year) {

        List<ScheduleResponseYearDto> scheduleResponseYearDto = scheduleService.findAllYearSchedule(calendarIdx, year);
        return ResponseEntity.ok(scheduleResponseYearDto);
    }

    // 일달력 조회
    @GetMapping("/{calendarIdx}/{year}/{month}/{day}")
    public ResponseEntity<List<ScheduleResponseDayDto>> getAllDaySchedule(
            @PathVariable(name = "calendarIdx") Long calendarIdx,
            @PathVariable(name = "year") int year,
            @PathVariable(name = "month") int month,
            @PathVariable(name = "day") int day) {

        List<ScheduleResponseDayDto> scheduleResponseDayDto = scheduleService.findScheduleByDay(calendarIdx, year, month, day);
        return ResponseEntity.ok(scheduleResponseDayDto);
    }


    // 일정 1개 조회
    @GetMapping("/{idx}")
    public ResponseEntity<ScheduleResponseDayDto> getOneSchedule(
            @PathVariable(name = "idx") Long idx) {
        ScheduleResponseDayDto scheduleResponseDayDto = scheduleService.findScheduleByOne(idx);
        return ResponseEntity.ok(scheduleResponseDayDto);
    }


    // 일정 입력
    @PostMapping(value = "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> saveSchedule(
            HttpServletRequest request,
            @CookieValue(value = "kakaoAccessToken",required = false) String kakaoAccessToken,
            @RequestPart(name = "scheduleRequest") @Valid ScheduleRequestInsertDto scheduleRequestInsertDto,
            @RequestPart(name = "imageFiles", required = false) @Schema(type = "array", format = "binary", description = "이미지 파일들") List<MultipartFile> imageFileList) {

        if (kakaoAccessToken != null && !kakaoAccessToken.isEmpty()) {
            System.out.println(kakaoAccessToken);
            kakaoMessageClient.sendMessage("Bearer "+kakaoAccessToken,
        """
                        {
                        "object_type": "text",
                        "text": "askdjfnldaksjfnlaksdjfnldaskjfn.",
                        "link": {
                            "web_url": "https://developers.kakao.com",
                            "mobile_web_url": "https://developers.kakao.com"
                        },
                        "button_title": "바로 확인"
                    }""");
        }

        if (imageFileList == null) {
            imageFileList = Collections.emptyList();
        }

        scheduleService.saveSchedule(scheduleRequestInsertDto, imageFileList);
        return ResponseEntity.status(HttpStatus.CREATED).body("Schedule created successfully");
    }


    // 일정 수정
    @PostMapping(value = "/update", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> updateSchedule(@RequestPart(name = "scheduleRequest") @Valid ScheduleRequestUpdateDto scheduleRequestUpdateDto,
                                                 @RequestPart(name = "imageFiles", required = false) List<MultipartFile> imageFileList) {
        scheduleService.updateSchedule(scheduleRequestUpdateDto, imageFileList);
        return ResponseEntity.status(HttpStatus.OK).body("Schedule updated successfully");
    }


    // 일정 삭제
    @DeleteMapping(value = "/delete/{idx}")
    public ResponseEntity<String> deleteSchedule(
            @PathVariable(name = "idx") Long idx,
            @RequestParam(name = "deleteAllRepeats", required = false, defaultValue = "false") boolean deleteAllRepeats,
            @RequestParam(name = "deleteOnlyThis", required = false, defaultValue = "false") boolean deleteOnlyThis,
            @RequestParam(name = "deleteAfter", required = false, defaultValue = "false") boolean deleteAfter) {
        scheduleService.deleteSchedule(idx, deleteAllRepeats, deleteOnlyThis, deleteAfter);
        return ResponseEntity.status(HttpStatus.OK).body("Schedule deleted successfully");
    }
}
