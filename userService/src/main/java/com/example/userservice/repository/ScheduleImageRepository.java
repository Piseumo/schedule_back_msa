package com.example.userservice.repository;

import com.example.userservice.entity.Schedule;
import com.example.userservice.entity.ScheduleImage;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ScheduleImageRepository extends JpaRepository<ScheduleImage, Long> {
    List<ScheduleImage> findBySchedule(Schedule schedule);
    void deleteByImgUrl(String imgUrl);

}
