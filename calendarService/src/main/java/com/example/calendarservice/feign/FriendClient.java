package com.example.calendarservice.feign;

import com.example.calendarservice.dto.response.UserSearchResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "friend-service", url = "${friend-service-url}")
public interface FriendClient {

    @GetMapping("friend-service/{userIdx}/list")
    List<UserSearchResponseDto> getFriendsList(@PathVariable(name = "idx") Long userId);

}
