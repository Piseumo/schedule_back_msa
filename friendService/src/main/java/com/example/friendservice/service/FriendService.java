package com.example.friendservice.service;

import com.example.friendservice.dto.response.FriendListResponseDto;
import com.example.friendservice.dto.response.UserSearchResponseDto;

import java.util.List;

public interface FriendService {

    void sendFriendRequest(Long requesterId, Long receiverId);

    List<Long> getFriendRequests(Long userId);

    void acceptFriendRequest(Long requesterId, Long receiverId);

    void rejectFriendRequest(Long friendRequestId);

    List<FriendListResponseDto> getFriendsList(Long userId);

    List<UserSearchResponseDto> searchUsersByUserName(Long userId, String userName);

    void deleteFriend(Long userId, Long deletedFriendId);
}
