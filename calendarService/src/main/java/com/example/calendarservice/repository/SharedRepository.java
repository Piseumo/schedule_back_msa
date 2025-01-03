package com.example.calendarservice.repository;

import com.example.calendarservice.entity.Comments;
import com.example.calendarservice.entity.Shared;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SharedRepository extends JpaRepository<Shared, Long> {

    // 전체 공개 (다이어리)
    @Query("SELECT sh FROM Shared sh " +
            "INNER JOIN Diary d ON sh.diaryIdx = d.idx " +
            "WHERE sh.friendIdx IS NULL AND d.calendars.userIdx IN :friendIds")
    List<Shared> findAllSharedWithAllAndFriendsDiaries(@Param("friendIds") List<Long> friendIds);

    // 전체 공개 (스케쥴)
    @Query("SELECT sh FROM Shared sh " +
            "INNER JOIN Schedule s ON sh.scheduleIdx = s.idx " +
            "WHERE sh.friendIdx IS NULL AND s.calendars.userIdx IN :friendIds")
    List<Shared> findAllSharedWithAllAndFriendsSchedules(@Param("friendIds") List<Long> friendIds);

    // 선택 친구 공개 (friendIdx가 나의 userIdx와 일치)
    @Query("SELECT s FROM Shared s WHERE s.friendIdx = :userId")
    List<Shared> findAllSharedWithChoose(@Param("userId") Long userId);


    // 개인 유저 공유 컨텐츠 조회
    @Query("SELECT s FROM Shared s " +
            "JOIN Schedule sc ON s.scheduleIdx = sc.idx " +
            "WHERE sc.calendars.userIdx = :userIdx " +
            "ORDER BY s.shareDateTime DESC")
    List<Shared> findSharedSchedulesByUser(@Param("userIdx") Long userIdx);

    @Query("SELECT s FROM Shared s " +
            "JOIN Diary d ON s.diaryIdx = d.idx " +
            "WHERE d.calendars.userIdx = :userIdx " +
            "ORDER BY s.shareDateTime DESC")
    List<Shared> findSharedDiariesByUser(@Param("userIdx") Long userIdx);


    // scheduleIdx로 sharedIdx 찾기
    @Query("SELECT s.sharedIdx FROM Shared s WHERE s.scheduleIdx = :scheduleIdx")
    List<Long> findSharedIdxByScheduleIdx(@Param("scheduleIdx") Long scheduleIdx);


    // diaryIdx로 sharedIdx 찾기
    @Query("SELECT s.sharedIdx FROM Shared s WHERE s.diaryIdx = :diaryIdx")
    List<Long> findSharedIdxByDiaryIdx(@Param("diaryIdx") Long diaryIdx);


    List<Shared> findByDiaryIdx(Long diaryIdx);

    List<Shared> findByScheduleIdx(Long diaryIdx);
}
