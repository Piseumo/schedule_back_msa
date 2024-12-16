package com.example.calendarservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Entity
@Table(name = "shared")
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Shared {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sh_idx")
    private Long sharedIdx;

    @Column(name = "s_idx")
    private Long scheduleIdx;

    @Column(name = "d_idx")
    private Long diaryIdx;

    @Column(name = "friend_idx")
    private Long friendIdx;


    @Column(name = "sh_date_time", nullable = false)
    private LocalDateTime shareDateTime;

    // 날짜 수정 메서드 추가
    public void updateDateTime() {
        this.shareDateTime = LocalDateTime.now();
    }

    public void updateDiaryIdx(Long diaryIdx) {
        this.diaryIdx = diaryIdx;
    }
}
