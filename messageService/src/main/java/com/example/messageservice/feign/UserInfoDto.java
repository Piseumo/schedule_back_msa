package com.example.messageservice.feign;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserInfoDto {
    private Long id;
    private String userName;
    private String profileImage;
}