package com.example.dailyLog.entity;

import com.example.dailyLog.constant.Provider;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "user")
@Getter @Setter
@ToString(exclude = "password")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "u_idx")
    private Long idx;

    @Column(name = "u_email", nullable = false, unique = true)
    private String email;

    @Column(name = "u_password", nullable = false)
    private String password;

    @Column(name = "u_nickname", nullable = false, unique = true)
    private String userName;

    @CreatedDate
    @Column(name = "u_joinday", updatable = false)
    private LocalDateTime joinDate;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "cal_idx", nullable = false)
    @JsonManagedReference
    private Calendars calendars;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Provider provider;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true ,fetch = FetchType.EAGER)
    @JsonManagedReference
    private ProfileImage profileImage;

    @Column(name = "access_token")
    private String accessToken;

    @Column(name = "refresh_token")
    private String refreshToken;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "access_token_expiry")
    private Date accessTokenExpiry;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = " refresh_token_expiry")
    private Date refreshTokenExpiry;


    @OneToMany(mappedBy = "requester", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Friend> sentRequests;

    @OneToMany(mappedBy = "receiver", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Friend> receivedRequests;

    @OneToMany(mappedBy = "user1", fetch = FetchType.LAZY)
    private List<ExchangeDiary> exchangeDiariesAsUser1;

    @OneToMany(mappedBy = "user2", fetch = FetchType.LAZY)
    private List<ExchangeDiary> exchangeDiariesAsUser2;

    @OneToMany(mappedBy = "creator", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ExchangeDiaryEntry> diaryEntries;
}
