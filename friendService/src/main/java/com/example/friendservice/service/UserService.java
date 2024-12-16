package com.example.friendservice.service;

import com.example.friendservice.dto.request.UserRequestInsertDto;
import com.example.friendservice.dto.request.UserRequestUpdateDto;
import com.example.friendservice.entity.User;
import jakarta.validation.Valid;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {
    User createUser(@Valid UserRequestInsertDto userRequestInsertDto);
    User findUserById(Long id);
    void updateUserName(@Valid UserRequestUpdateDto userRequestUpdateDto);
    void updateProfileImage(Long id, MultipartFile imageFile);
    void deleteUser(String email, String authToken);
}
