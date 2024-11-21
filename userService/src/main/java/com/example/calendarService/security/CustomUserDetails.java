package com.example.calendarService.security;

import com.example.calendarService.entity.Calendars;
import com.example.calendarService.entity.ProfileImage;
import com.example.calendarService.entity.User;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@AllArgsConstructor
@Getter
@Builder
@Setter
public class CustomUserDetails implements UserDetails {
    private Long idx;
    private String email;
    private String password;
    private String userName;
    private ProfileImage profileImage;
    private Calendars calendarIdx;

    public CustomUserDetails(User user) {
        this.idx = user.getIdx();
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.userName = user.getUserName();
        this.profileImage = user.getProfileImage();
        this.calendarIdx = user.getCalendars();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList(); // 빈 컬렉션 반환
    }

    @Override
    public String getPassword(){
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    public String getUserName() {
        return userName;
    }

}