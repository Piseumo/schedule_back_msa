package com.example.friendservice.controller;

import com.example.friendservice.dto.request.AcceptFriendRequestDto;
import com.example.friendservice.dto.request.FriendRequestDto;
import com.example.friendservice.dto.response.UserSearchResponseDto;
import com.example.friendservice.service.FriendService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/friend")
@RequiredArgsConstructor
public class FriendController {

    private final FriendService friendService;

    // 친구 요청 보내기
    @PostMapping("/request")
    public ResponseEntity<Map<String, String>> sendFriendRequest(@RequestBody FriendRequestDto friendRequestDto) {
        friendService.sendFriendRequest(friendRequestDto);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Friend request sent successfully.");
        return ResponseEntity.ok(response);
    }

    // 친구 요청 조회
    @GetMapping("/{idx}/requests")
    public ResponseEntity<List<UserSearchResponseDto>> getFriendRequests(@PathVariable(name = "idx") Long userId) {
        List<UserSearchResponseDto> friendRequests = friendService.getFriendRequests(userId);

        return ResponseEntity.ok(friendRequests);
    }

    // 친구 요청 수락
    @PostMapping("/accept")
    public ResponseEntity<String> acceptFriendRequest(@RequestBody AcceptFriendRequestDto acceptFriendRequestDto){
        Long requesterId = acceptFriendRequestDto.getRequesterId();
        Long receiverId = acceptFriendRequestDto.getReceiverId();

        friendService.acceptFriendRequest(requesterId, receiverId);
        return ResponseEntity.ok("Friend request accepted.");
    }

    // 친구 요청 거절
    @PostMapping("/reject/{rejectedRequestId}")
    public ResponseEntity<String> rejectFriendRequest(@PathVariable(name = "rejectedRequestId") Long friendRequestId) {
        friendService.rejectFriendRequest(friendRequestId);
        return ResponseEntity.ok("Friend request rejected.");
    }

    // 친구 목록 조회
    @GetMapping("/{idx}/list")
    public List<UserSearchResponseDto> getFriendsList(@PathVariable(name = "idx") Long userId) {
        List<UserSearchResponseDto> friends = friendService.getFriendsList(userId);
        return friends;
    }


    // 유저 검색
    @GetMapping("/search")
    public ResponseEntity<List<UserSearchResponseDto>> searchUsers(@RequestParam(name = "userId") Long userId,
                                                                   @RequestParam(name = "userName") String userName) {
        List<UserSearchResponseDto> users = friendService.searchUsersByUserName(userId, userName);
        return ResponseEntity.ok(users);
    }


    // 친구 삭제
    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteFriend(@RequestParam(name = "userId") Long userId,
                                               @RequestParam(name = "friendId") Long deleteFriendId) {
        friendService.deleteFriend(userId, deleteFriendId);
        return ResponseEntity.ok("Friend deleted.");
    }

}
