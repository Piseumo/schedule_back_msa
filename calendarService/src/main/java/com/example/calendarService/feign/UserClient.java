package com.example.calendarService.feign;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "user-service" , url = "${user-service-url}")
public interface UserClient {

    @PostMapping("/{userId}")
    void getUser(@PathVariable(value = "userId") String userId);

}
