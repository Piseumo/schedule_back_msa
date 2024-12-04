package com.example.calendarservice.repository;

import com.example.calendarservice.entity.DiaryImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiaryImageRepository extends JpaRepository<DiaryImage, Long> {
    void deleteByImgUrl(String imgUrl);

}
