package com.example.dailyLog.feign;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserInfoDto {
    private String nickname;
    private String profileImage;
}