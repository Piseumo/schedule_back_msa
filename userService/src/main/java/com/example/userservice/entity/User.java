package com.example.userservice.entity;

import com.example.userservice.constant.Provider;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "users")
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
}
