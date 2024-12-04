package com.example.dailyLog.service;

import com.example.dailyLog.dto.request.KakaoUserInfoDto;
import com.example.dailyLog.dto.response.KakaoLoginResponseDto;
import com.example.dailyLog.entity.User;

public interface KakaoLoginService {
    String getKakaoAccessToken(String code);
    KakaoUserInfoDto getKakaoUserInfo(String accessToken);
    User createKakaoUser(KakaoUserInfoDto kakaoUserInfoDto, String accessToken);
}
