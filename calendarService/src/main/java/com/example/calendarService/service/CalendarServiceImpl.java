package com.example.calendarService.service;

import com.example.calendarService.constant.Theme;
import com.example.calendarService.entity.Calendars;
import com.example.calendarService.entity.Diary;
import com.example.calendarService.repository.CalendarRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;


@RequiredArgsConstructor
public class CalendarServiceImpl implements CalendarService{

    private final CalendarRepository calendarRepository;

    @Override
    @Transactional
    public void createCalendar(Long userId) {
        Calendars createCalendar = Calendars.builder()
                .theme(Theme.LIGHT)
                .userIdx(userId)
                .build();
        calendarRepository.save(createCalendar);
    }
}
