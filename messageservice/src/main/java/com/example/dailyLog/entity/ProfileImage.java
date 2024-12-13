package com.example.dailyLog.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "profile_image")
@Getter @Setter
@ToString
public class ProfileImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "p_idx")
    private Long idx;

    @Column(name = "p_img_name", nullable = false)
    private String imgName;

    @Column(name = "p_ori_img_name", nullable = false)
    private String oriImgName;

    @Column(name = "p_img_url", nullable = false)
    private String imgUrl;

    @OneToOne
    @JoinColumn(name = "u_idx")
    @JsonBackReference
    private User user;

}