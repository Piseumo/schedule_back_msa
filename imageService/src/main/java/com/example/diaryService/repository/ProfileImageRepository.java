package com.example.diaryService.repository;

import com.example.diaryService.entity.ProfileImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProfileImageRepository extends JpaRepository<ProfileImage, Long> {
    Optional<ProfileImage> findByUserIdx(Long userIdx);
}
