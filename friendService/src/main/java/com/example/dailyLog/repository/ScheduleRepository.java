package com.example.dailyLog.repository;

import com.example.dailyLog.entity.Calendars;
import com.example.dailyLog.entity.Schedule;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    // 월간 일정 조회
    @Query("SELECT s FROM Schedule s WHERE s.calendars.idx = :calendarIdx AND s.start BETWEEN :start AND :end")
    List<Schedule> findByCalendarsIdxAndStartBetween(@Param("calendarIdx") Long calendarIdx, @Param("start") LocalDateTime start, @Param("end") LocalDateTime end);

    // 일간 일정 조회
    @Query("SELECT s FROM Schedule s WHERE s.start >= :start AND s.end <= :end AND s.calendars.idx = :calendarIdx")
    List<Schedule> findSchedulesInDay(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end, @Param("calendarIdx") Long calendarIdx);

    List<Schedule> findByStartBetween(LocalDateTime start, LocalDateTime end);

    // 특정 반복 그룹의 모든 일정 조회
    @Query("SELECT s FROM Schedule s WHERE s.repeatGroupId = :repeatGroupId")
    List<Schedule> findByRepeatGroupId(@Param("repeatGroupId") Long repeatGroupId);

    // 특정 반복 그룹의 특정 날짜 이후 일정 삭제
    @Modifying
    @Transactional
    @Query("DELETE FROM Schedule s WHERE s.repeatGroupId = :repeatGroupId AND s.start >= :start")
    void deleteAfterDate(@Param("repeatGroupId") Long repeatGroupId, @Param("start") LocalDateTime start);
}
