package com.example.dailyLog.service;

import com.example.dailyLog.dto.request.ExchangeDiaryEntryRequestInsertDto;
import com.example.dailyLog.dto.request.ExchangeDiaryEntryRequestUpdateDto;
import com.example.dailyLog.dto.response.ExchangeDiaryEntryListResponseDto;
import com.example.dailyLog.dto.response.ExchangeDiaryResponseSummaryDto;
import com.example.dailyLog.entity.ExchangeDiaryEntry;

import java.util.List;

public interface ExchangeDiaryEntryService {

    // 일기 생성
    ExchangeDiaryEntry createEntry(ExchangeDiaryEntryRequestInsertDto requestDto);

    // 일기 수정
    ExchangeDiaryEntry updateEntry(ExchangeDiaryEntryRequestUpdateDto updateDto);

    // 일기 삭제
    void deleteEntry(Long entryId);

    // 교환 일기 미리보기 조회(제목,참석자)
    List<ExchangeDiaryResponseSummaryDto> getUserExchangeDiaries(Long userId);

    // 교환 일기 전체 내용 리스트 형식 반환
    List<ExchangeDiaryEntryListResponseDto> getEntriesByDiaryId(Long userId, Long diaryId);


}
