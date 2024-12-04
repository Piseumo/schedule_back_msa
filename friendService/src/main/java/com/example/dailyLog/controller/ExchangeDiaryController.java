package com.example.dailyLog.controller;

import com.example.dailyLog.dto.response.UserSearchResponseDto;
import com.example.dailyLog.service.ExchangeDiaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/exchange-diary")
@RequiredArgsConstructor
public class ExchangeDiaryController {

    private final ExchangeDiaryService exchangeDiaryService;

    // 교환 일기 요청 전송
    @PostMapping("/request")
    public ResponseEntity<Long> sendExchangeDiaryRequest(@RequestParam(name = "requesterId") Long requesterId,
                                                           @RequestParam(name = "receiverId") Long receiverId,
                                                           @RequestParam(name = "groupName") String groupName) {
        Long diaryId = exchangeDiaryService.sendExchangeDiaryRequest(requesterId, receiverId, groupName);
        return ResponseEntity.ok(diaryId);
    }

    // 교환 일기 요청 목록 조회
    @GetMapping("/{idx}/requests")
    public ResponseEntity<List<UserSearchResponseDto>> getExchangeDiaryRequests(@PathVariable(name = "idx") Long userId) {
        List<UserSearchResponseDto> requests = exchangeDiaryService.getExchangeDiaryRequests(userId);
        return ResponseEntity.ok(requests);
    }

    // 교환 일기 요청 수락
    @PostMapping("/accept")
    public ResponseEntity<String> acceptExchangeDiaryRequest(@RequestParam(name = "requesterId") Long requesterId,
                                                             @RequestParam(name = "receiverId") Long receiverId) {
        exchangeDiaryService.acceptExchangeDiaryRequest(requesterId, receiverId);
        return ResponseEntity.ok("Exchange diary request accepted.");
    }

    // 교환 일기 요청 거절
    @PostMapping("/reject")
    public ResponseEntity<String> rejectExchangeDiaryRequest(@RequestParam(name ="exchangeDiaryId") Long exchangeDiaryId) {
        exchangeDiaryService.rejectExchangeDiaryRequest(exchangeDiaryId);
        return ResponseEntity.ok("Exchange diary request rejected.");
    }

    // 교환 일기 삭제
    @DeleteMapping("/{exchangeDiaryId}")
    public ResponseEntity<String> deleteExchangeDiary(@PathVariable(name = "exchangeDiaryId") Long exchangeDiaryId) {
        exchangeDiaryService.deleteExchangeDiary(exchangeDiaryId);
        return ResponseEntity.ok("Exchange diary deleted successfully.");
    }
}
