package com.example.dailyLog.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "scheduleImage")
@Getter @Setter
@ToString
public class ScheduleImage {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "i_idx")
    private Long idx;

    @Column(name = "img_name", nullable = false)
    private String imgName;

    @Column(name = "ori_img_name",  nullable = false)
    private String oriImgName;

    @Column(name = "img_url", nullable = false)
    private String imgUrl;

    @ManyToOne
    @JoinColumn(name = "s_idx", nullable = false)
    private Schedule schedule;

}
