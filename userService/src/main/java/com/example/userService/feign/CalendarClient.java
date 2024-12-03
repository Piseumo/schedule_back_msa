package com.example.userService.feign;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "calendar-service" , url = "${calandar-service-url}")
public interface CalendarClient {

    @PostMapping()
    void createCalendar(@PathVariable(value = "userId") Long userId);

}
