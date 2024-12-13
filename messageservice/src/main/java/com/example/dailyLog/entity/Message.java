package com.example.dailyLog.entity;

import com.example.dailyLog.constant.ExchangeDiaryStatus;
import com.example.dailyLog.constant.MessageStatus;
import com.example.dailyLog.constant.Status;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@Table(name = "message")
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "m_idx")
    private Long idx;

    @Column(name = "sender_idx", nullable = false)
    private Long senderId;

    @Column(name = "receiver_idx", nullable = false)
    private Long receiverId;

    @Enumerated(EnumType.STRING)
    @Column(name = "m_status", nullable = false)
    private MessageStatus status;

    @Column(name = "m_timestamp", updatable = false)
    private LocalDateTime timeStamp;

}
