package com.example.calendarService.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@ToString
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class ShareWithFriends {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sw_idx")
    private Long idx;

    @JoinColumn(name = "s_idx")
    private Schedule schedule;

    @JoinColumn(name = "d_idx")
    private Diary diary;

    @JoinColumn(name = "f_idx")
    private Friend friend;

    @Column(name = "sw_date")
    private LocalDateTime shareDate;
}
