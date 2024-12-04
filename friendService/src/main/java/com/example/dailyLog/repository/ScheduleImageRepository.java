package com.example.dailyLog.repository;

import com.example.dailyLog.entity.DiaryImage;
import com.example.dailyLog.entity.Schedule;
import com.example.dailyLog.entity.ScheduleImage;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ScheduleImageRepository extends JpaRepository<ScheduleImage, Long> {
    List<ScheduleImage> findBySchedule(Schedule schedule);
    void deleteByImgUrl(String imgUrl);

}
