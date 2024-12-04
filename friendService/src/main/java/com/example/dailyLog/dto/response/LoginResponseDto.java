package com.example.dailyLog.dto.response;

import lombok.*;

@Getter
@Builder
@Setter
@AllArgsConstructor
public class LoginResponseDto {
    private Long idx;
    private String accessToken;
    private String email;
    private String userName;
    private String profileImageUrl;
    private String refreshToken;
    private Long calendarIdx;
}
