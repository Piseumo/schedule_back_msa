package com.example.calendarService.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface CalendarService {
    void createCalendar(Long userId);
}
