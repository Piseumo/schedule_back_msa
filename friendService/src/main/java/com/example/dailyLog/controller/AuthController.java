package com.example.dailyLog.controller;

import com.example.dailyLog.constant.Provider;
import com.example.dailyLog.dto.request.KakaoUserInfoDto;
import com.example.dailyLog.dto.request.LoginRequestDto;
import com.example.dailyLog.dto.request.UserRequestInsertDto;
import com.example.dailyLog.dto.response.LoginResponseDto;
import com.example.dailyLog.entity.ProfileImage;
import com.example.dailyLog.entity.User;
import com.example.dailyLog.security.CustomUserDetails;
import com.example.dailyLog.service.KakaoLoginService;
import com.example.dailyLog.service.LoginService;
import com.example.dailyLog.service.UserDetailsServiceImpl;
import com.example.dailyLog.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final LoginService loginService;
    private final UserDetailsServiceImpl userDetailsServiceImpl;
    private final KakaoLoginService kakaoLoginService;


    @PostMapping("/join")
    public ResponseEntity<String> join(@Valid @RequestBody UserRequestInsertDto userRequestInsertDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            // 유효성 검사 오류 처리
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            for (FieldError fieldError : fieldErrors) {
                // 오류 메시지 처리
                System.out.println(fieldError.getField() + ": " + fieldError.getDefaultMessage());
            }
            return ResponseEntity.badRequest().body("비밀번호는 8자~20자 사이여야 하며, 영문, 숫자, 특수문자를 각 하나씩 사용하셔야 합니다.");
        }

        userService.createUser(userRequestInsertDto);
        return ResponseEntity.ok("회원가입이 완료되었습니다.");
    }


    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto requestDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(requestDto.getEmail(), requestDto.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        userDetails = (CustomUserDetails) userDetailsServiceImpl.loadUserByUsername(userDetails.getEmail());
        User user = new User();
        user.setIdx(userDetails.getIdx());
        user.setEmail(userDetails.getEmail());
        user.setPassword(userDetails.getPassword());
        user.setUserName(userDetails.getUserName());
        user.setProfileImage(userDetails.getProfileImage());
        user.setCalendars(userDetails.getCalendarIdx());
        user.setProvider(Provider.LOCAL);

        LoginResponseDto responseDto = loginService.generateAndSaveTokens(user);
        System.out.println(responseDto);

        return ResponseEntity.ok(responseDto);
    }


    @GetMapping("/kakao/login")
    public ResponseEntity<LoginResponseDto> kakaoLogin(@RequestParam(value = "code") String code) {
        String accessToken = kakaoLoginService.getKakaoAccessToken(code);
        KakaoUserInfoDto kakaoUserInfo = kakaoLoginService.getKakaoUserInfo(accessToken);
        User user = kakaoLoginService.createKakaoUser(kakaoUserInfo, code);

        // JWT 생성 및 저장
        LoginResponseDto responseDto = loginService.generateAndSaveTokens(user);

        return ResponseEntity.ok(responseDto);
    }
}
