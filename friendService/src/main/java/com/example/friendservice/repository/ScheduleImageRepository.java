package com.example.friendservice.repository;

import com.example.friendservice.entity.Schedule;
import com.example.friendservice.entity.ScheduleImage;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ScheduleImageRepository extends JpaRepository<ScheduleImage, Long> {
    List<ScheduleImage> findBySchedule(Schedule schedule);
    void deleteByImgUrl(String imgUrl);

}
