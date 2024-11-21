package com.example.userService.service;

import com.example.userService.dto.response.LoginResponseDto;
import com.example.userService.entity.User;
import org.springframework.stereotype.Service;

@Service
public interface LoginService {
    String loginUser(String email, String password);
    LoginResponseDto generateAndSaveTokens(User user);
}
