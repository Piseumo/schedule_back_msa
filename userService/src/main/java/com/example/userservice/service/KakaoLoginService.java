package com.example.userservice.service;

import com.example.userservice.dto.request.KakaoUserInfoDto;
import com.example.userservice.entity.User;

public interface KakaoLoginService {
    String getKakaoAccessToken(String code);
    KakaoUserInfoDto getKakaoUserInfo(String accessToken);
    User createKakaoUser(KakaoUserInfoDto kakaoUserInfoDto, String accessToken);
}
