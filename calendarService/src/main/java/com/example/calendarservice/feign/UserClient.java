package com.example.calendarservice.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user-service", url = "${users-service-url}")
public interface UserClient {

    // userName 가져오기
    @GetMapping("/user-service/name/{userIdx}")
    public String getUserName(@PathVariable(name = "userIdx") Long userIdx);
}
