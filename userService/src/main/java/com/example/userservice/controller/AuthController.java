package com.example.userservice.controller;

import com.example.userservice.constant.Provider;
import com.example.userservice.dto.request.KakaoUserInfoDto;
import com.example.userservice.dto.request.LoginRequestDto;
import com.example.userservice.dto.request.UserRequestInsertDto;
import com.example.userservice.dto.response.LoginResponseDto;
import com.example.userservice.entity.User;
import com.example.userservice.security.CustomUserDetails;
import com.example.userservice.service.KakaoLoginService;
import com.example.userservice.service.LoginService;
import com.example.userservice.service.UserDetailsServiceImpl;
import com.example.userservice.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
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
        log.error(requestDto.toString());
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

        //카카오 토큰 클라이언트쪽에 저장
        Cookie cookie = new Cookie("kakaoAccessToken", accessToken);
        cookie.setHttpOnly(true);
        cookie.setSecure(false); // HTTPS 환경에서만 동작
        cookie.setPath("/");
        cookie.setMaxAge(7 * 24 * 60 * 60); // 7일 동안 유효

        // 쿠키를 헤더로 추가
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.SET_COOKIE,
                String.format("%s=%s; Path=%s; Max-Age=%d; HttpOnly;",
                        cookie.getName(),
                        cookie.getValue(),
                        cookie.getPath(),
                        cookie.getMaxAge()));

        return new ResponseEntity<>(responseDto, headers, HttpStatus.OK);
    }
}
