package com.example.userservice.service;

import com.example.userservice.dto.request.UserRequestInsertDto;
import com.example.userservice.dto.request.UserRequestUpdateDto;
import com.example.userservice.dto.response.UserResponseDto;
import com.example.userservice.entity.User;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {
    ResponseEntity<UserResponseDto> createUser(@Valid UserRequestInsertDto userRequestInsertDto);
    User findUserById(Long id);
    void updateUserName(@Valid UserRequestUpdateDto userRequestUpdateDto);
    void updateProfileImage(Long id, MultipartFile imageFile);
    void deleteUser(String email, String authToken);
}