package com.example.dailyLog.service;

import com.example.dailyLog.constant.Status;
import com.example.dailyLog.dto.response.FriendListResponseDto;
import com.example.dailyLog.dto.response.UserSearchResponseDto;
import com.example.dailyLog.entity.Friend;
import com.example.dailyLog.entity.User;

import java.util.List;

public interface FriendService {

    void sendFriendRequest(Long requesterId, Long receiverId);

    List<UserSearchResponseDto> getFriendRequests(Long userId);

    void acceptFriendRequest(Long requesterId, Long receiverId);

    void rejectFriendRequest(Long friendRequestId);

    List<FriendListResponseDto> getFriendsList(Long userId);

    List<UserSearchResponseDto> searchUsersByUserName(Long userId, String userName);
}
