package com.example.dailyLog.service;

import com.example.dailyLog.dto.response.LoginResponseDto;
import com.example.dailyLog.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public interface LoginService {
    String loginUser(String email, String password);
    LoginResponseDto generateAndSaveTokens(User user);
}
