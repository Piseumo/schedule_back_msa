package com.example.dailyLog.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserSearchResponseDto {

    private Long userId;      // 요청한 사용자 ID
    private Long diaryId;     // 교환 일기 ID

    private String userName;

    private String profileImageUrl;

    private String email;

    // 친구 요청 조회 시 사용할 생성자 (diaryId 없음)
    public UserSearchResponseDto(Long userId, String userName, String profileImageUrl, String email) {
        this.userId = userId;
        this.userName = userName;
        this.profileImageUrl = profileImageUrl;
        this.email = email;
    }

}
