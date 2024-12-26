package com.example.friendservice.entity;

import com.example.friendservice.constant.Status;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Data
@EntityListeners(AuditingEntityListener.class)
@Table(
        name = "friends",
        uniqueConstraints = @UniqueConstraint(columnNames = {"req_idx", "rec_idx"}) // 복합 유니크 키 설정
)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Friend {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "f_idx")
    private Long idx;

    @Column(name = "req_idx", nullable = false)
    private Long requesterId;

    @Column(name = "rec_idx", nullable = false)
    private Long receiverId;

    @Enumerated(EnumType.STRING)
    @Column(name = "f_status", nullable = false)
    private Status status;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

}
