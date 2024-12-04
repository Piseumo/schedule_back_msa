package com.example.dailyLog.repository;

import com.example.dailyLog.dto.response.StatisticsMonthDto;
import com.example.dailyLog.entity.Diary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StatisticsRepository extends JpaRepository<Diary, Long> {

    @Query("SELECT d.category, COUNT(d) " +
            "FROM Diary d JOIN d.calendars c JOIN c.user u " +
            "WHERE u.idx = :userIdx " +
            "GROUP BY d.category")
    List<Object[]> findCategoryCountsAsObjectArray(@Param("userIdx") Long userIdx);


    @Query("SELECT COUNT(d) FROM Diary d JOIN d.calendars c JOIN c.user u WHERE u.idx = :userIdx")
    Long findTotalCountByUser(@Param("userIdx") Long userIdx);


    @Query("SELECT FUNCTION('DAY', d.date) AS day, d.category, COUNT(d) " +
            "FROM Diary d " +
            "WHERE d.calendars.user.idx = :userIdx AND FUNCTION('YEAR', d.date) = :year AND FUNCTION('MONTH', d.date) = :month " +
            "GROUP BY FUNCTION('DAY', d.date), d.category " +
            "ORDER BY day")
    List<Object[]> findDailyCategoryCountsByUserAndMonth(
            @Param("userIdx") Long userIdx,
            @Param("year") int year,
            @Param("month") int month
    );


    @Query("SELECT FUNCTION('MONTH', d.date) AS month, d.category, COUNT(d) " +
            "FROM Diary d WHERE d.calendars.user.idx = :userIdx AND FUNCTION('YEAR', d.date) = :year " +
            "GROUP BY FUNCTION('MONTH', d.date), d.category ORDER BY month")
    List<Object[]> findMonthlyCategoryCountsByUserAndYear(@Param("userIdx") Long userIdx, @Param("year") int year);

}
