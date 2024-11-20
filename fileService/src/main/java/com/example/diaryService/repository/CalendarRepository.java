package com.example.diaryService.repository;

import com.example.diaryService.entity.Calendars;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CalendarRepository extends JpaRepository<Calendars, Long> {

}
