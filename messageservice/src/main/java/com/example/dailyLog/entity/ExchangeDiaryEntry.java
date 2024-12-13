package com.example.dailyLog.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@Table(name = "exchange_diary_entry")
public class ExchangeDiaryEntry {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "e_idx")
    private Long idx;

    @ManyToOne
    @JoinColumn(name = "ed_idx", nullable = false)
    private ExchangeDiary exchangeDiary;

    @ManyToOne
    @JoinColumn(name = "u_idx", nullable = false)
    private User creator;

    @Column(name = "e_title", nullable = false)
    private String title;

    @Column(name = "e_content", nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(name = "ee_created_at", updatable = false)
    private LocalDateTime createdAt;
}
