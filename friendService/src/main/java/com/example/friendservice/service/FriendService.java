package com.example.friendservice.service;

import com.example.friendservice.dto.request.FriendRequestDto;
import com.example.friendservice.dto.response.UserSearchResponseDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface FriendService {

    ResponseEntity<String> sendFriendRequest(FriendRequestDto friendRequestDto);

    List<UserSearchResponseDto> getFriendRequests(Long userId);

    void acceptFriendRequest(Long requesterId, Long receiverId);

    void rejectFriendRequest(Long friendRequestId);

    List<UserSearchResponseDto> getFriendsList(Long userId);

    List<UserSearchResponseDto> searchUsersByUserName(Long userId, String userName);

    void deleteFriend(Long userId, Long deletedFriendId);
}
