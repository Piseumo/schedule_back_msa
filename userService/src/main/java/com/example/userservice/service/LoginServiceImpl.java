package com.example.userservice.service;

import com.example.userservice.dto.response.LoginResponseDto;
import com.example.userservice.entity.User;
import com.example.userservice.exception.commonException.error.BizException;
import com.example.userservice.exception.loginException.EmailNotFoundException;
import com.example.userservice.exception.loginException.LoginErrorCode;
import com.example.userservice.repository.UserRepository;
import com.example.userservice.security.providers.JwtTokenProvider;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {

    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    @Override
    public String loginUser(String email, String password) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new EmailNotFoundException(LoginErrorCode.EMAIL_NOT_FOUND));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new BizException(LoginErrorCode.INVALID_CREDENTIALS);
        }

        // 토큰 생성 및 저장
        return generateAndSaveTokens(user).getAccessToken();
    }

    @Transactional
    @Override
    public LoginResponseDto generateAndSaveTokens(User user) {
        String accessToken = createAndSaveAccessToken(user);
        String refreshToken = createAndSaveRefreshToken(user);

        User dbUser = userRepository.save(user);

        return LoginResponseDto.builder()
                .idx(user.getIdx())
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .email(user.getEmail())
                .userName(user.getUserName())
                .profileImageUrl(dbUser.getProfileImage() != null ? dbUser.getProfileImage().getImgUrl() : "")
                .build();
    }

    private String createAndSaveAccessToken(User user) {
        String accessToken = jwtTokenProvider.createAccessToken(user.getEmail());
        user.setAccessToken(accessToken);
        user.setAccessTokenExpiry(jwtTokenProvider.getAccessTokenExpiryDate());
        return accessToken;
    }

    private String createAndSaveRefreshToken(User user) {
        String refreshToken = jwtTokenProvider.createRefreshToken(user.getEmail());
        user.setRefreshToken(refreshToken);
        user.setRefreshTokenExpiry(jwtTokenProvider.getRefreshTokenExpiryDate());
        return refreshToken;
    }
}
