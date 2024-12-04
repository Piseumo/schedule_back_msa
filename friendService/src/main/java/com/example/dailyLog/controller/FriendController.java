package com.example.dailyLog.controller;

import com.example.dailyLog.dto.response.FriendListResponseDto;
import com.example.dailyLog.dto.response.UserSearchResponseDto;
import com.example.dailyLog.entity.Friend;
import com.example.dailyLog.service.FriendService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/friend")
@RequiredArgsConstructor
public class FriendController {

    private final FriendService friendService;

    // 친구 요청 보내기
    @PostMapping("/request")
    public ResponseEntity<String> sendFriendRequest(@RequestParam(name = "requesterId") Long requesterId, @RequestParam(name = "receiverId") Long receiverId) {
        friendService.sendFriendRequest(requesterId, receiverId);
        return ResponseEntity.ok("Friend request sent successfully.");
    }

    // 친구 요청 조회
    @GetMapping("/{idx}/requests")
    public ResponseEntity<List<UserSearchResponseDto>> getFriendRequests(@PathVariable(name = "idx") Long userId) {
        List<UserSearchResponseDto> friendRequests = friendService.getFriendRequests(userId);
        return ResponseEntity.ok(friendRequests);
    }

    // 친구 요청 수락
    @PostMapping("/accept")
    public ResponseEntity<String> acceptFriendRequest(@RequestParam(name = "requesterId") Long requesterId, @RequestParam(name = "receiverId") Long receiverId){
        friendService.acceptFriendRequest(requesterId, receiverId);
        return ResponseEntity.ok("Friend request accepted.");
    }

    // 친구 요청 거절
    @PostMapping("/reject")
    public ResponseEntity<String> rejectFriendRequest(@RequestParam(name = "rejectedRequestId") Long friendRequestId) {
        friendService.rejectFriendRequest(friendRequestId);
        return ResponseEntity.ok("Friend request rejected.");
    }

    // 친구 목록 조회
    @GetMapping("/{idx}/list")
    public ResponseEntity<List<FriendListResponseDto>> getFriendsList(@PathVariable(name = "idx") Long userId) {
        List<FriendListResponseDto> friends = friendService.getFriendsList(userId);
        return ResponseEntity.ok(friends);
    }


    // 유저 검색
    @GetMapping("/search")
    public ResponseEntity<List<UserSearchResponseDto>> searchUsers(@RequestParam(name = "userId") Long userId,
                                                                   @RequestParam(name = "userName") String userName) {
        List<UserSearchResponseDto> users = friendService.searchUsersByUserName(userId, userName);
        return ResponseEntity.ok(users);
    }

}
