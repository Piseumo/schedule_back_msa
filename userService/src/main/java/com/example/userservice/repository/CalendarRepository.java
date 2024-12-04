package com.example.userservice.repository;

import com.example.userservice.entity.Calendars;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CalendarRepository extends JpaRepository<Calendars, Long> {

}
