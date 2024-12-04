package com.example.userservice.service;

import com.example.userservice.dto.response.LoginResponseDto;
import com.example.userservice.entity.User;
import org.springframework.stereotype.Service;

@Service
public interface LoginService {
    String loginUser(String email, String password);
    LoginResponseDto generateAndSaveTokens(User user);
}
