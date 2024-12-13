package com.example.dailyLog.service;

import com.example.dailyLog.constant.MessageStatus;
import com.example.dailyLog.dto.request.MessageRequestDto;
import com.example.dailyLog.dto.response.ConversationDto;
import com.example.dailyLog.entity.Message;
import com.example.dailyLog.feign.UserFeignClient;
import com.example.dailyLog.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {
    private final MessageRepository messageRepository;
    private final UserFeignClient userFeignClient;


    @Transactional
    public Message sendMessage(MessageRequestDto messageRequestDto) {

        return messageRepository.save(Message.builder()
                .senderId(messageRequestDto.getSenderId())
                .receiverId(messageRequestDto.getReceiverId())
                .content(messageRequestDto.getContent())
                .status(MessageStatus.UNREAD)
                .build());
    }

    @Transactional
    public List<Message> getMessages(Long userId, Long otherUserId) {
        return List.of();
    }

    @Transactional
    public List<ConversationDto> getRecentConversations(Long userId) {
        return List.of();
    }
}
