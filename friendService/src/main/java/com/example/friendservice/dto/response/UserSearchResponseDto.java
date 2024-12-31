package com.example.friendservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor // 모든 필드에 대한 생성자
@NoArgsConstructor  // 기본 생성자
public class UserSearchResponseDto {

    private Long userId;
    private Long friendId;
    private String userName;
    private String profileImageUrl;
    private String email;
}
