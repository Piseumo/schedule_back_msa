package com.example.dailyLog.repository;

import com.example.dailyLog.constant.ExchangeDiaryStatus;
import com.example.dailyLog.entity.ExchangeDiary;
import com.example.dailyLog.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ExchangeDiaryRepository extends JpaRepository<ExchangeDiary, Long> {

    List<ExchangeDiary> findByUser1OrUser2(User user1, User user2);

    Optional<ExchangeDiary> findByUser1AndUser2(User user1, User user2);

    // 특정 두 사용자가 이미 교환 일기를 가지고 있는지 확인하는 메서드
    boolean existsByUser1AndUser2(User user1, User user2);

    // 특정 사용자와 관련된 모든 교환 일기 조회
    List<ExchangeDiary> findAllByUser2AndStatus(User user2, ExchangeDiaryStatus status);
}
