package com.example.userservice.dto.request;

import com.example.userservice.entity.ProfileImage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class UserInfoDto {
    private Long id;
    private String userName;
    private ProfileImage profileImage;
}