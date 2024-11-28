package com.example.calendarService.entity;

import com.example.calendarService.constant.Category;
import com.example.calendarService.constant.Share;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "diary")
@Getter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Diary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "d_idx")
    private Long idx;

    @Column(name = "d_title", nullable = false)
    private String title;

    @Column(name = "d_content", columnDefinition = "TEXT")
    private String content;

    @Column(name = "d_date", nullable = false)
    private LocalDate date;


    @Column(name = "d_category", nullable = false)
    @Enumerated(EnumType.STRING)
    private Category category = Category.DAILY;

    @Column(name = "d_share", nullable = false)
    @Enumerated(EnumType.STRING)
    private Share share;

    @ManyToOne
    @JoinColumn(name = "cal_idx", nullable = false)
    private Calendars calendars;

    @OneToMany(mappedBy = "diary", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DiaryImage> diaryImages = new ArrayList<>();

}
