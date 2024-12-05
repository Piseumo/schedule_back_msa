package com.example.friendservice.feign;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "user-service", url = "${user-service-url}")
public interface UserFeignClient {



}
