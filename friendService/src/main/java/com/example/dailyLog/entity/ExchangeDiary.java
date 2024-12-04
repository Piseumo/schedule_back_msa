package com.example.dailyLog.entity;

import com.example.dailyLog.constant.ExchangeDiaryStatus;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@Table(name = "exchange_diary")
public class ExchangeDiary {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ed_idx")
    private Long idx;

    @ManyToOne
    @JoinColumn(name = "user1_id", nullable = false)
    private User user1;

    @ManyToOne
    @JoinColumn(name = "user2_id", nullable = false)
    private User user2;

    @Column(name = "group_name", nullable = false)
    private String groupName;

    @Enumerated(EnumType.STRING)
    @Column(name = "e_status", nullable = false)
    private ExchangeDiaryStatus status;

    @Column(name = "e_created_at", updatable = false)
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "exchangeDiary", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ExchangeDiaryEntry> entries =  new ArrayList<>();
}
