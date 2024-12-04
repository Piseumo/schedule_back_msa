package com.example.dailyLog.dto.response;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class KakaoLoginResponseDto {
    private Long id;
    private String connectedAt;
    private KakaoAccount kakaoAccount;

    @Data
    @NoArgsConstructor
    public static class KakaoAccount {
        private String email;
        private Profile profile;

        @Data
        @NoArgsConstructor
        public static class Profile {
            private String nickname;
            private String profileImageUrl;
        }
    }

    public String getEmail() {
        return kakaoAccount != null ? kakaoAccount.getEmail() : null;
    }

    public String getUserName() {
        return kakaoAccount != null && kakaoAccount.getProfile() != null ? kakaoAccount.getProfile().getNickname() : null;
    }

    public String getProfileImageUrl() {
        return kakaoAccount != null && kakaoAccount.getProfile() != null ? kakaoAccount.getProfile().getProfileImageUrl() : null;
    }
}
