package com.example.calendarservice.service;

import com.example.calendarservice.constant.Theme;
import com.example.calendarservice.entity.Calendars;
import com.example.calendarservice.repository.CalendarRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
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

    @Override
    @Transactional
    public void deleteCalendar(Long userId) {

        calendarRepository.deleteById(userId);
    }
}
