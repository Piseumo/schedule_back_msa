package com.example.userservice.service;

import com.example.userservice.dto.response.UserSearchResponseDto;

import java.util.List;

public interface ExchangeDiaryService {

    // 교환 일기 요청 보내기
    Long sendExchangeDiaryRequest(Long requesterId, Long receiverId, String groupName);

    // 교환 일기 요청 조회
    List<UserSearchResponseDto> getExchangeDiaryRequests(Long userId);

    // 요청 수락 및 거절
    void acceptExchangeDiaryRequest(Long requesterId, Long receiverId);
    void rejectExchangeDiaryRequest(Long exchangeDiaryId);

    // 교환 일기 삭제
    void deleteExchangeDiary(Long exchangeDiaryId);

}
