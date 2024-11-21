package com.example.userService.service;

import com.example.userService.entity.ProfileImage;
import com.example.userService.entity.User;
import org.springframework.web.multipart.MultipartFile;

public interface ProfileImageService {
    ProfileImage saveProfileImage(MultipartFile imageFile, User user) throws Exception ;
    String getProfileImage(Long idx);
    ProfileImage saveProfileImageFromUrl(byte[] imageBytes, User user);
}
