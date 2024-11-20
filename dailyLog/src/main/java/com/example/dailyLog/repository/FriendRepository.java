package com.example.dailyLog.repository;

import com.example.dailyLog.constant.Status;
import com.example.dailyLog.entity.Friend;
import com.example.dailyLog.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface FriendRepository extends JpaRepository<Friend, Long> {
    // 특정 유저가 받은 모든 친구 요청 조회 (수락 대기 상태)
    List<Friend> findByReceiverAndStatus(User receiver, Status status);

    boolean existsByRequesterAndReceiver(User requester, User receiver);

    // 두 유저 간의 친구 상태 조회 (중복 요청 방지)
    Optional<Friend> findByRequesterAndReceiver(User requester, User receiver);

    //친구 검색 관련
    @Query("SELECT f FROM Friend f WHERE (f.requester = :user OR f.receiver = :user) AND f.status = 'ACCEPTED'")
    List<Friend> findFriendsByUser(@Param("user") User user);
}
