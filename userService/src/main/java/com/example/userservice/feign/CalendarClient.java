package com.example.userservice.feign;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "calendar-service" , url = "${calendar-service-url}")
public interface CalendarClient {

    @PostMapping("calendar-service/create/{userId}")
    String createCalendar(@PathVariable(value = "userId") Long userId);
}
