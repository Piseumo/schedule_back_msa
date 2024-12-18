package com.example.notificationService.entity;

import com.example.notificationService.constant.NotificationType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "notification")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  // 기본 키 (자동 증가)

    @Column(nullable = false, unique = true)
    private String notificationId;  // 고유 알림 식별자 (username + timestamp)

    @Column(nullable = false)
    private String receiver;  // 알림 수신자

    @Column(nullable = false)
    private String content;   // 알림 메시지 내용

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private NotificationType notificationType;  // 알림 유형 (공지, 댓글 등)

    @Column(nullable = false)
    private String url;  // 알림 클릭 시 이동할 URL

    @Column(nullable = false, length = 1)
    private char readYn;  // 읽음 여부 ('N' 기본값)

    @Column(nullable = false, length = 1)
    private char deletedYn;  // 삭제 여부 ('N' 기본값)

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;  // 생성일자

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();  // 자동 생성일자
    }
}