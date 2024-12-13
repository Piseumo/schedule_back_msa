package com.example.dailyLog.repository;

import com.example.dailyLog.entity.Diary;
import com.example.dailyLog.entity.DiaryImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DiaryImageRepository extends JpaRepository<DiaryImage, Long> {
    void deleteByImgUrl(String imgUrl);

}
