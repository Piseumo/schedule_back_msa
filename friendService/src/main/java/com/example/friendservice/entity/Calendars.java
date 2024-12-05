package com.example.friendservice.entity;

import com.example.friendservice.constant.Theme;
import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "calendars")
@Getter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Calendars {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cal_idx")
    private Long idx;

    @Column(name = "c_theme", nullable = false)
    @Enumerated(EnumType.STRING)
    private Theme theme;

    @OneToOne
    @JoinColumn(name = "u_idx2")
    private User user;
}
