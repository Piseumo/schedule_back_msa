package com.example.notificationService.service;

import com.example.notificationService.dto.response.LoginResponseDto;
import com.example.notificationService.entity.User;
import org.springframework.stereotype.Service;

@Service
public interface LoginService {
    String loginUser(String email, String password);
    LoginResponseDto generateAndSaveTokens(User user);
}
