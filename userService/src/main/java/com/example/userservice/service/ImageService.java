package com.example.userservice.service;

import com.example.userservice.entity.*;
import org.springframework.web.multipart.MultipartFile;

public interface ImageService {
    ProfileImage saveProfileImage(MultipartFile imageFile, User user) throws Exception ;
    String getProfileImage(Long idx);
    ProfileImage saveProfileImageFromUrl(byte[] imageBytes, User user);
}
