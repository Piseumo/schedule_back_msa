package com.example.calendarService.service;

import com.example.calendarService.dto.request.KakaoUserInfoDto;
import com.example.calendarService.entity.User;

public interface KakaoLoginService {
    String getKakaoAccessToken(String code);
    KakaoUserInfoDto getKakaoUserInfo(String accessToken);
    User createKakaoUser(KakaoUserInfoDto kakaoUserInfoDto, String accessToken);
}
