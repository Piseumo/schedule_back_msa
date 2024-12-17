package com.example.calendarservice.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "comments")
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
public class Comments {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "com_idx")
    private Long commentsIdx;

    @Column(name = "u_idx")
    private Long userIdx;

    @Column(name = "sh_idx")
    private Long sharedIdx;

    @Column(name = "com_date_time", nullable = false)
    private LocalDateTime dateTime;

    @Column(name = "com_content")
    private String content;
}
