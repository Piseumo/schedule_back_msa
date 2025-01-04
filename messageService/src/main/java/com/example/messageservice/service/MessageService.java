package com.example.messageservice.service;

import com.example.messageservice.dto.request.MessageRequestDto;
import com.example.messageservice.dto.response.ConversationDto;
import com.example.messageservice.entity.Message;

import java.util.List;

public interface MessageService {

    Message sendMessage(MessageRequestDto messageRequestDto);

    List<Message> getMessages(Long userId, Long otherUserId);

    List<ConversationDto> getRecentConversations(Long userId);

    void deleteMessage(Long userId, Long friendId);

}
