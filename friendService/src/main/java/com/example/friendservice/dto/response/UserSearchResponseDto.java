package com.example.friendservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserSearchResponseDto {

    private Long userId;
    private Long friendId;
    private String userName;
    private String profileImageUrl;
    private String email;
}
