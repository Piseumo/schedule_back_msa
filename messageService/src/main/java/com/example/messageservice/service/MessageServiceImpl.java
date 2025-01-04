package com.example.messageservice.service;

import com.example.messageservice.constant.MessageStatus;
import com.example.messageservice.dto.request.MessageRequestDto;
import com.example.messageservice.dto.response.ConversationDto;
import com.example.messageservice.entity.Message;
import com.example.messageservice.feign.UserFeignClient;
import com.example.messageservice.feign.UserInfoDto;
import com.example.messageservice.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {
    private final MessageRepository messageRepository;
    private final UserFeignClient userFeignClient;

    @Transactional
    @Override
    public Message sendMessage(MessageRequestDto messageRequestDto) {

        return messageRepository.save(Message.builder()
                .senderId(messageRequestDto.getSenderId())
                .receiverId(messageRequestDto.getReceiverId())
                .content(messageRequestDto.getContent())
                .status(MessageStatus.UNREAD)
                .build());
    }

    @Transactional
    @Override
    public List<Message> getMessages(Long userId, Long otherUserId) {
        // 두 사용자 간의 대화 메시지를 시간 순으로 조회
        return messageRepository.findMessagesBetweenUsers(userId, otherUserId);
    }

    @Transactional
    @Override
    public List<ConversationDto> getRecentConversations(Long userId) {
        // 최근 대화 메시지 조회
        List<Message> recentMessages = messageRepository.findRecentConversations(userId);

        // 대화 상대별로 메시지를 그룹화 및 추가 정보 조회
        Map<Long, ConversationDto> conversationMap = new HashMap<>();

        for (Message message : recentMessages) {
            Long otherUserId = message.getSenderId().equals(userId) ? message.getReceiverId() : message.getSenderId();

            if (!conversationMap.containsKey(otherUserId)) {
                // 상대방 정보 조회 (Feign Client 사용)
                UserInfoDto userInfo = userFeignClient.getUserInfo(otherUserId);

                // ConversationDto 생성
                conversationMap.put(otherUserId, ConversationDto.builder()
                        .userId(userId)
                        .otherUserId(otherUserId)
                        .profileImage(userInfo.getProfileImage())
                        .userName(userInfo.getUserName())
                        .lastMessage(message.getContent())
                        .lastMessageTime(message.getTimestamp())
                        .build());
            }
        }

        // 최신 메시지 기준으로 정렬
        return new ArrayList<>(conversationMap.values())
                .stream()
                .sorted(Comparator.comparing(ConversationDto::getLastMessageTime).reversed())
                .toList();
    }

    @Transactional
    @Override
    public void deleteMessage(Long userId, Long friendId) {
        messageRepository.deleteMessagesBetweenUsers(userId, friendId);
    }
}
