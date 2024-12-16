package com.example.friendservice.service;

import com.example.friendservice.dto.response.LoginResponseDto;
import com.example.friendservice.entity.User;
import org.springframework.stereotype.Service;

@Service
public interface LoginService {
    String loginUser(String email, String password);
    LoginResponseDto generateAndSaveTokens(User user);
}
