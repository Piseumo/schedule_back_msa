package com.example.messageservice.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user-service", url = "${user-service-url}")
public interface UserFeignClient {

    @GetMapping("/user-service/{userId}")
    UserInfoDto getUserInfo(@PathVariable(name = "userId") Long userId);

}
