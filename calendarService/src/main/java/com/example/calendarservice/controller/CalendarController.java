package com.example.calendarservice.controller;

import com.example.calendarservice.service.CalendarService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("calendar-service")
@RequiredArgsConstructor
public class CalendarController {
    private final CalendarService calendarService;


    @PostMapping("/create/{userId}")
    String createCalendar(@PathVariable(value = "userId") Long userId){
        calendarService.createCalendar(userId);
        return "성공하였습니다.";
    }

}
