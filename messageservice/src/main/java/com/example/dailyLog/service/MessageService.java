package com.example.dailyLog.service;

import com.example.dailyLog.dto.request.MessageRequestDto;
import com.example.dailyLog.dto.response.ConversationDto;
import com.example.dailyLog.entity.Message;

import java.util.List;

public interface MessageService {

    // 메시지 전송
    Message sendMessage(MessageRequestDto messageRequestDto);

    // 특정 사용자와의 메시지 내역 조회
    List<Message> getMessages(Long userId, Long otherUserId);

    // 최근 대화 목록 조회
    List<ConversationDto> getRecentConversations(Long userId);

}
