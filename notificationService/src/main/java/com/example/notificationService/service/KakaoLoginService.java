package com.example.notificationService.service;

import com.example.notificationService.dto.request.KakaoUserInfoDto;
import com.example.notificationService.entity.User;

public interface KakaoLoginService {
    String getKakaoAccessToken(String code);
    KakaoUserInfoDto getKakaoUserInfo(String accessToken);
    User createKakaoUser(KakaoUserInfoDto kakaoUserInfoDto, String accessToken);
}
