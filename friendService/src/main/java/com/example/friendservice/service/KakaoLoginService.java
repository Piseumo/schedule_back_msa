package com.example.friendservice.service;

import com.example.friendservice.dto.request.KakaoUserInfoDto;
import com.example.friendservice.entity.User;

public interface KakaoLoginService {
    String getKakaoAccessToken(String code);
    KakaoUserInfoDto getKakaoUserInfo(String accessToken);
    User createKakaoUser(KakaoUserInfoDto kakaoUserInfoDto, String accessToken);
}
