package com.example.friendservice.feign;

import com.example.friendservice.dto.response.UserSearchResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "user-service", url = "${user-service-url}")
public interface UserFeignClient {

    // 친구 아닌 유저 검색
    @GetMapping("/user-service/search")
    List<UserSearchResponseDto> searchUsers(@RequestParam String userName, @RequestParam List<Long> friendIds);

    // 친구 요청 목록 조회
    @GetMapping("/user-service/request")
    List<UserSearchResponseDto> friendRequestList(@RequestParam List<Long> friendId);

    @GetMapping("/user-service/friends")
    List<UserSearchResponseDto> getFriendsList(@RequestParam List<Long> friendsId);

}
