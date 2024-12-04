package com.example.calendarservice.entity;

import com.example.calendarservice.constant.Theme;
import com.example.calendarservice.service.UserService;
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

    @Column(name = "u_idx", nullable = false)
    private Long userIdx;

    @OneToOne
    @JoinColumn(name = "u_idx2")
    private User user;

}
