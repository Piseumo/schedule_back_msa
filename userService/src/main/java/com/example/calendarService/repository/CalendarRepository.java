package com.example.calendarService.repository;

import com.example.calendarService.entity.Calendars;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CalendarRepository extends JpaRepository<Calendars, Long> {

}
