package com.example.calendarService.service;

import com.example.calendarService.dto.response.LoginResponseDto;
import com.example.calendarService.entity.User;
import org.springframework.stereotype.Service;

@Service
public interface LoginService {
    String loginUser(String email, String password);
    LoginResponseDto generateAndSaveTokens(User user);
}
