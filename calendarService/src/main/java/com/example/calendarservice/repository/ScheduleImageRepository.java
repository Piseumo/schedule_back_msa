package com.example.calendarservice.repository;

import com.example.calendarservice.entity.Schedule;
import com.example.calendarservice.entity.ScheduleImage;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ScheduleImageRepository extends JpaRepository<ScheduleImage, Long> {
    List<ScheduleImage> findBySchedule(Schedule schedule);
    void deleteByImgUrl(String imgUrl);

}
