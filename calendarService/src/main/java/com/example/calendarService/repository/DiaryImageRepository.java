package com.example.calendarService.repository;

import com.example.calendarService.entity.DiaryImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiaryImageRepository extends JpaRepository<DiaryImage, Long> {
    void deleteByImgUrl(String imgUrl);

}
