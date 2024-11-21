package com.example.userService.service;

import com.example.userService.dto.request.KakaoUserInfoDto;
import com.example.userService.entity.User;

public interface KakaoLoginService {
    String getKakaoAccessToken(String code);
    KakaoUserInfoDto getKakaoUserInfo(String accessToken);
    User createKakaoUser(KakaoUserInfoDto kakaoUserInfoDto, String accessToken);
}
