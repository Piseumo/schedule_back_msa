package com.example.friendservice.repository;

import com.example.friendservice.constant.Category;
import com.example.friendservice.entity.Diary;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.List;

public interface DiaryRepository extends JpaRepository<Diary, Long> {

    List<Diary> findByCalendarsIdx(Long calendarIdx);
    List<Diary> findByCalendarsIdxAndCategory(Long calendarIdx, Category category);
    List<Diary> findByCalendarsIdxAndDate(Long calendarIdx, LocalDate date);
    List<Diary> findByCalendarsIdxAndDateBetween(Long calendarIdx, LocalDate start, LocalDate end);

    // 캘린더 일기 검색
//    List<Diary> findByTitleContainingIgnoreCase(String title);
//    List<Diary> findByContentContainingIgnoreCase(String content);
}
