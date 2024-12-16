package com.example.messageservice.repository;

import com.example.messageservice.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {

    @Query("SELECT m FROM Message m WHERE " +
            "(m.senderId = :userId AND m.receiverId = :otherUserId) " +
            "OR (m.senderId = :otherUserId AND m.receiverId = :userId) " +
            "ORDER BY m.timestamp ASC")
    List<Message> findMessagesBetweenUsers(@Param("userId") Long userId, @Param("otherUserId") Long otherUserId);

    // 최근 대화 상대 목록 조회
    @Query("SELECT m FROM Message m WHERE m.senderId = :userId OR m.receiverId = :userId " +
            "GROUP BY CASE WHEN m.senderId = :userId THEN m.receiverId ELSE m.senderId END, " +
            "m.timestamp " +
            "ORDER BY MAX(m.timestamp) DESC")
    List<Message> findRecentConversations(@Param("userId") Long userId);

}
