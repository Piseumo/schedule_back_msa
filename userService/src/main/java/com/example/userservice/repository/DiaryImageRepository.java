package com.example.userservice.repository;

import com.example.userservice.entity.DiaryImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiaryImageRepository extends JpaRepository<DiaryImage, Long> {
    void deleteByImgUrl(String imgUrl);

}
