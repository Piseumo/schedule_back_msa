package com.example.dailyLog.repository;

import com.example.dailyLog.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {

    List<Message> findBySenderIdAndReceiverIdOrReceiverIdAndSenderIdOrderByTimestamp(
            Long senderId, Long receiverId, Long receiverIdAlt, Long senderIdAlt);

    // 최근 대화 상대 목록 조회
    @Query("SELECT m FROM Message m WHERE m.senderId = :userId OR m.receiverId = :userId " +
            "GROUP BY CASE WHEN m.senderId = :userId THEN m.receiverId ELSE m.senderId END, " +
            "m.timestamp " +
            "ORDER BY MAX(m.timestamp) DESC")
    List<Message> findRecentConversations(@Param("userId") Long userId);

}
