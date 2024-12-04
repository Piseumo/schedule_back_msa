package com.example.dailyLog.repository;

import com.example.dailyLog.entity.ProfileImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProfileImageRepository extends JpaRepository<ProfileImage, Long> {
    Optional<ProfileImage> findByUserIdx(Long userIdx);
}
