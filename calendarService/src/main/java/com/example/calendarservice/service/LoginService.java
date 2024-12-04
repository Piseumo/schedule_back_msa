package com.example.calendarservice.service;

import com.example.calendarservice.dto.response.LoginResponseDto;
import com.example.calendarservice.entity.User;
import org.springframework.stereotype.Service;

@Service
public interface LoginService {
    String loginUser(String email, String password);
    LoginResponseDto generateAndSaveTokens(User user);
}
