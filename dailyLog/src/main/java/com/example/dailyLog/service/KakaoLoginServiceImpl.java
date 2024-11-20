package com.example.dailyLog.service;

import com.example.dailyLog.constant.Provider;
import com.example.dailyLog.constant.Theme;
import com.example.dailyLog.dto.KakaoTokenDto;
import com.example.dailyLog.dto.request.KakaoUserInfoDto;
import com.example.dailyLog.entity.Calendars;
import com.example.dailyLog.entity.ProfileImage;
import com.example.dailyLog.entity.User;
import com.example.dailyLog.repository.UserRepository;
import com.example.dailyLog.security.providers.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.InputStream;
import java.net.URL;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class KakaoLoginServiceImpl implements KakaoLoginService {

    private final UserRepository userRepository;
    private final Environment environment;
    private final ImageService imageService;

    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    public String getKakaoAccessToken(String code) {
        String url = "https://kauth.kakao.com/oauth/token";

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "authorization_code");
        body.add("client_id", environment.getProperty("kakao.client-id"));
        body.add("redirect_uri", environment.getProperty("kakao.redirect-uri"));
        body.add("code", code);
        body.add("client_secret", environment.getProperty("kakao.client-secret"));

        try {
            System.out.println("Request Body: " + body);

            return sendRequestForToken(url, body).getAccess_token();
        } catch (HttpClientErrorException e) {
            throw new RuntimeException("Failed to get Kakao Access Token: " + e.getMessage());
        }
    }

    private KakaoTokenDto sendRequestForToken(String url, MultiValueMap<String, String> body) {
        return restTemplate.postForObject(url, new HttpEntity<>(body, createHeaders()), KakaoTokenDto.class);
    }

    @Override
    public KakaoUserInfoDto getKakaoUserInfo(String accessToken) {
        String url = "https://kapi.kakao.com/v2/user/me";

        HttpHeaders headers = createHeaders();
        headers.add("Authorization", "Bearer " + accessToken);

        try {
            ResponseEntity<KakaoUserInfoDto> response = restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(headers), KakaoUserInfoDto.class);
            return response.getBody();
        } catch (HttpClientErrorException e) {
            throw new RuntimeException("Failed to get Kakao User Info: " + e.getMessage());
        }
    }

    @Override
    public User createKakaoUser(KakaoUserInfoDto kakaoUserInfoDto, String accessToken) {
        if (kakaoUserInfoDto.getKakaoAccount() == null || kakaoUserInfoDto.getKakaoAccount().getEmail() == null) {
            throw new RuntimeException("Failed to retrieve email from Kakao response");
        }
        return userRepository.findByEmail(kakaoUserInfoDto.getKakaoAccount().getEmail())
                .map(user -> updateUserEntity(user, kakaoUserInfoDto, accessToken))
                .orElseGet(() -> createUserEntity(kakaoUserInfoDto, accessToken));
    }

    private User createUserEntity(KakaoUserInfoDto kakaoUserInfoDto, String accessToken) {
        // Calendar 생성
        Calendars calendars = Calendars.builder().theme(Theme.LIGHT).build();

        // User 엔티티 생성
        User user = User.builder()
                .email(kakaoUserInfoDto.getKakaoAccount().getEmail())
                .password(UUID.randomUUID().toString()) // 일반 로그인과 충돌을 피하기 위한 임시 패스워드
                .userName(kakaoUserInfoDto.getKakaoAccount().getProfile().getNickname())
                .provider(Provider.KAKAO)
                .calendars(calendars)
                .accessToken(accessToken)
                .build();

        user = userRepository.save(user);

        // 카카오 프로필 이미지 URL 가져오기
        String profileImageUrl = kakaoUserInfoDto.getKakaoAccount().getProfile().getProfileImageUrl();

        // 프로필 이미지 처리
        ProfileImage profileImage = new ProfileImage();
        if (profileImageUrl == null || profileImageUrl.isEmpty()) {
            // 카카오 프로필 이미지가 없는 경우 기본 이미지 설정
            profileImage.setImgUrl("default_profile_image_url");  // 기본 이미지 URL 설정
            profileImage.setImgName("default_image_name");  // 기본 이미지 이름 설정
            profileImage.setOriImgName("default_original_name"); // 기본 원본 이미지 이름 설정
        } else {
            // 카카오 프로필 이미지가 있는 경우 다운로드 및 저장
            try (InputStream in = new URL(profileImageUrl).openStream()) {
                byte[] imageBytes = in.readAllBytes();
                profileImage = imageService.saveProfileImageFromUrl(imageBytes, user);
            } catch (Exception e) {
                throw new RuntimeException("Failed to download Kakao profile image: " + e.getMessage());
            }
        }

        // 사용자와 프로필 이미지 연결
        user.setProfileImage(profileImage);
        profileImage.setUser(user);

        return userRepository.save(user);
    }


    private User updateUserEntity(User user, KakaoUserInfoDto kakaoUserInfoDto, String accessToken) {
        user.setAccessToken(accessToken);
        String profileImageUrl = kakaoUserInfoDto.getKakaoAccount().getProfile().getProfileImageUrl();
        if (profileImageUrl != null && !profileImageUrl.isEmpty()) {
            user.getProfileImage().setImgUrl(profileImageUrl);
        }
        return userRepository.save(user);
    }

    private HttpHeaders createHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
        return headers;
    }
}
