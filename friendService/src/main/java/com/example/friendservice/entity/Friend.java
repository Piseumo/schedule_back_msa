package com.example.friendservice.entity;

import com.example.friendservice.constant.Status;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Data
@EntityListeners(AuditingEntityListener.class)
@Table(name = "friend",uniqueConstraints = @UniqueConstraint(columnNames = {"req_idx", "rec_idx"}))
@NoArgsConstructor
public class Friend {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "f_idx")
    private Long idx;

    @Column(name = "u_idx1")
    private Long requesterId;

    @Column(name = "u_idx2")
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
