package com.example.diaryService.service;

import com.example.diaryService.dto.response.LoginResponseDto;
import com.example.diaryService.entity.User;
import org.springframework.stereotype.Service;

@Service
public interface LoginService {
    String loginUser(String email, String password);
    LoginResponseDto generateAndSaveTokens(User user);
}
