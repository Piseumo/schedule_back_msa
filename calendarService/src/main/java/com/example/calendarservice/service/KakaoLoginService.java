package com.example.calendarservice.service;

import com.example.calendarservice.dto.request.KakaoUserInfoDto;
import com.example.calendarservice.entity.User;

public interface KakaoLoginService {
    String getKakaoAccessToken(String code);
    KakaoUserInfoDto getKakaoUserInfo(String accessToken);
    User createKakaoUser(KakaoUserInfoDto kakaoUserInfoDto, String accessToken);
}
