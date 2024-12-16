package com.example.friendservice.repository;

import com.example.friendservice.entity.Calendars;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CalendarRepository extends JpaRepository<Calendars, Long> {

}
