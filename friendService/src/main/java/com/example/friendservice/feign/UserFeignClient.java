package com.example.friendservice.feign;

import com.example.friendservice.dto.response.UserSearchResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "user-service", url = "${user-service-url}")
public interface UserFeignClient {

    @GetMapping("/user-service/search")
    public List<UserSearchResponseDto> searchUsers(@RequestParam String userName, @RequestParam List<Long> friendIds);

    @GetMapping("/user-service/request")


}
