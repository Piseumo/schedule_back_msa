package com.example.notificationService.controller;

import com.example.notificationService.dto.request.ExchangeDiaryEntryRequestInsertDto;
import com.example.notificationService.dto.request.ExchangeDiaryEntryRequestUpdateDto;
import com.example.notificationService.dto.response.ExchangeDiaryEntryListResponseDto;
import com.example.notificationService.dto.response.ExchangeDiaryResponseSummaryDto;
import com.example.notificationService.repository.ExchangeDiaryEntryRepository;
import com.example.notificationService.service.ExchangeDiaryEntryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/exchange-diary-entry")
@RequiredArgsConstructor
public class ExchangeDiaryEntryController {
    private final ExchangeDiaryEntryRepository entryRepository;
    private final ExchangeDiaryEntryService exchangeDiaryEntryService;

    @PostMapping("/create")
    public ResponseEntity<String> createExchangeDiary(@RequestBody ExchangeDiaryEntryRequestInsertDto requestInsertDto) {
        exchangeDiaryEntryService.createEntry(requestInsertDto);
        return ResponseEntity.status(HttpStatus.CREATED).body("ExchangeDiary created successfully");

    }

    @PostMapping("/update")
    public ResponseEntity<String> updateExchangeDiary(@RequestBody ExchangeDiaryEntryRequestUpdateDto requestUpdateDto) {
        exchangeDiaryEntryService.updateEntry(requestUpdateDto);
        return ResponseEntity.status(HttpStatus.OK).body("ExchangeDiary updated successfully");

    }

    @DeleteMapping("/delete/{idx}")
    public ResponseEntity<String> deleteExchangeDiary(@PathVariable(name = "idx") Long edIdx) {
        exchangeDiaryEntryService.deleteEntry(edIdx);
        return ResponseEntity.status(HttpStatus.OK).body("ExchangeDiary deleted successfully");
    }

    @GetMapping("/summary/{idx}")
    public List<ExchangeDiaryResponseSummaryDto> getSummaryDiary(@PathVariable(name = "idx") Long userId) {
        return exchangeDiaryEntryService.getUserExchangeDiaries(userId);

    }

    @GetMapping("/exchangeDiaryList/{idx}")
    public List<ExchangeDiaryEntryListResponseDto> getExchangeDiaryList(@PathVariable(name = "idx") Long diaryId,
                                                                        @RequestParam(name = "userId") Long userId) {
        return exchangeDiaryEntryService.getEntriesByDiaryId(userId, diaryId);
    }
}
