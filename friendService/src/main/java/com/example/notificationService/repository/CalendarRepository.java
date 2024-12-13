package com.example.notificationService.repository;

import com.example.notificationService.entity.Calendars;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CalendarRepository extends JpaRepository<Calendars, Long> {

}
