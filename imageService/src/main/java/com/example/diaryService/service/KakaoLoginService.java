package com.example.diaryService.service;

import com.example.diaryService.dto.request.KakaoUserInfoDto;
import com.example.diaryService.entity.User;

public interface KakaoLoginService {
    String getKakaoAccessToken(String code);
    KakaoUserInfoDto getKakaoUserInfo(String accessToken);
    User createKakaoUser(KakaoUserInfoDto kakaoUserInfoDto, String accessToken);
}
