package com.example.calendarService.entity;

import com.example.calendarService.constant.ExchangeDiaryStatus;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data @Setter @Getter
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
