package com.example.messageservice.entity;

import com.example.messageservice.constant.MessageStatus;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "message")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "m_idx")
    private Long idx;

    @Column(name = "sender_idx", nullable = false)
    private Long senderId;

    @Column(name = "receiver_idx", nullable = false)
    private Long receiverId;

    @Column(name = "m_content", columnDefinition = "TEXT")
    private String content;

    @Enumerated(EnumType.STRING)
    @Column(name = "m_status", nullable = false)
    private MessageStatus status;

    @CreatedDate
    @Column(name = "m_timestamp", updatable = false)
    private LocalDateTime timestamp;
}
