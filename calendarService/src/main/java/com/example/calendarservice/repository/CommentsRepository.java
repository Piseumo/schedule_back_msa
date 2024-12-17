package com.example.calendarservice.repository;

import com.example.calendarservice.entity.Comments;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentsRepository extends JpaRepository<Comments, Long> {
    List<Comments> findByScheduleIdx(Long scheduleIdx);
    List<Comments> findByDiaryIdx(Long diaryIdx);
}
