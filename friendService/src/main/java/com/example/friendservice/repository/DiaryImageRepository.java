package com.example.friendservice.repository;

import com.example.friendservice.entity.DiaryImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiaryImageRepository extends JpaRepository<DiaryImage, Long> {
    void deleteByImgUrl(String imgUrl);

}
