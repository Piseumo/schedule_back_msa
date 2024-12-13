package com.example.dailyLog.feign;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "user-service", url = "${user-service-url}")
public class UserFeignClient {

}
