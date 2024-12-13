package com.example.notificationService.entity;

import com.example.notificationService.constant.NotificationType;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "notification")
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "n_idx")
    private Long n_Id;

    @Enumerated(EnumType.STRING)
    @Column(name = "n_type", nullable = false)
    private NotificationType n_Type;
    // 알림 타입 (FRIEND ,COMMENT ,SHARE ,MESSAGE )

    @Column(nullable = false)
    private Long receiverId;

    @Column
    private Long senderId;

    @Column
    private Long shId;

    @Column
    private Long comId;

    @Column
    private Long mmsId;

    @Column(nullable = false)
    private Boolean n_read = false; // 읽음 여부

    @Column(nullable = false)
    private String n_content;
}