package com.example.calendarservice.controller;

import com.example.calendarservice.service.CalendarService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("calendar-service")
@RequiredArgsConstructor
@CrossOrigin
public class CalendarController {
    private final CalendarService calendarService;


    @PostMapping("/create/{userId}")
    String createCalendar(@PathVariable(value = "userId") Long userId){
        calendarService.createCalendar(userId);
        return "성공하였습니다.";
    }

}
