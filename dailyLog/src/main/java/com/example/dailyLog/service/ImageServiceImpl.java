package com.example.dailyLog.service;

import com.example.dailyLog.entity.*;
import com.example.dailyLog.exception.imageException.EmptyFileData;
import com.example.dailyLog.exception.imageException.FileUploadError;
import com.example.dailyLog.exception.imageException.ImageErrorCode;
import com.example.dailyLog.exception.imageException.InvalidFileName;
import com.example.dailyLog.repository.DiaryImageRepository;
import com.example.dailyLog.repository.ProfileImageRepository;
import com.example.dailyLog.repository.ScheduleImageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ImageServiceImpl implements ImageService {

    @Value("${ImgLocation}")
    private String imageLocation;

    @Value("${ProfileImgLocation}")
    private String profileImageLocation;

    private final DiaryImageRepository diaryImageRepository;
    private final ScheduleImageRepository scheduleImageRepository;
    private final ProfileImageRepository profileImageRepository;
    private final FileService fileService;

    // 유효성 검사 메소드
    public void validateImageFile(MultipartFile imageFile) {
        if (imageFile == null || imageFile.isEmpty()) {
            throw new EmptyFileData(ImageErrorCode.EMPTY_FILE_DATA);
        }

        String oriImgName = imageFile.getOriginalFilename();
        if (oriImgName == null || !oriImgName.contains(".")) {
            throw new InvalidFileName(ImageErrorCode.INVALID_FILE_NAME);
        }
    }

    @Transactional
    @Override
    public DiaryImage saveDiaryImage(MultipartFile imageFile, Diary diary) {

        validateImageFile(imageFile);

        try {
            String oriImgName = imageFile.getOriginalFilename();
            String savedFileName = fileService.uploadFile(imageLocation, oriImgName, imageFile.getBytes());
            String imageUrl = "/images/" + savedFileName;

            // Image 엔티티 생성 및 설정
            DiaryImage diaryImage = new DiaryImage();
            diaryImage.setImgName(savedFileName);
            diaryImage.setOriImgName(oriImgName);
            diaryImage.setImgUrl(imageUrl);
            diaryImage.setDiary(diary);

            return diaryImageRepository.save(diaryImage);
        } catch (Exception e) {
            throw new FileUploadError(ImageErrorCode.FILE_UPLOAD_ERROR);
        }
    }

    @Transactional
    @Override
    public ScheduleImage saveScheduleImage(MultipartFile imageFile, Schedule schedule) {

        validateImageFile(imageFile);

        try {
            String oriImgName = imageFile.getOriginalFilename();
            String savedFileName = fileService.uploadFile(imageLocation, oriImgName, imageFile.getBytes());
            String imageUrl = "/images/" + savedFileName;

            // Image 엔티티 생성 및 설정
            ScheduleImage scheduleImage = new ScheduleImage();
            scheduleImage.setImgName(savedFileName);
            scheduleImage.setOriImgName(oriImgName);
            scheduleImage.setImgUrl(imageUrl);
            scheduleImage.setSchedule(schedule);

            return scheduleImageRepository.save(scheduleImage);
        } catch (Exception e) {
            throw new FileUploadError(ImageErrorCode.FILE_UPLOAD_ERROR);
        }
    }

    @Transactional
    @Override
    public ProfileImage saveProfileImage(MultipartFile imageFile, User user) throws Exception {
        try {

            ProfileImage existingImage = user.getProfileImage();
            if (existingImage != null) {
                fileService.deleteFile(existingImage.getImgUrl());
                profileImageRepository.delete(existingImage);
            }

            String oriImgName = imageFile.getOriginalFilename();
            String savedFileName = fileService.uploadFile(profileImageLocation, oriImgName, imageFile.getBytes());
            String imageUrl = "/profileImages/" + savedFileName;


            // ProfileImage 엔티티 생성 및 설정
            ProfileImage profileImage = new ProfileImage();
            profileImage.setImgName(savedFileName);
            profileImage.setOriImgName(oriImgName);
            profileImage.setImgUrl(imageUrl);
            profileImage.setUser(user);

            user.setProfileImage(profileImage);
            return profileImageRepository.save(profileImage);
        } catch (Exception e) {
            throw new FileUploadError(ImageErrorCode.FILE_UPLOAD_ERROR);
        }
    }

    // 카카오 유저 이미지를 받아 저장하는 메서드
    @Transactional
    @Override
    public ProfileImage saveProfileImageFromUrl(byte[] imageBytes, User user) {
        try {
            // 기본 프로필 이미지 URL과 파일명 설정
            String oriImgName;
            String savedFileName;
            String imageUrl;

            if (imageBytes == null || imageBytes.length == 0) {
                // 이미지 데이터가 없으면 기본 이미지 설정
                oriImgName = "default_original_name";
                savedFileName = "default_image_name.jpg";
                imageUrl = "/defaultImages/" + savedFileName; // 기본 이미지가 저장된 경로

                ProfileImage profileImage = new ProfileImage();
                profileImage.setImgName(savedFileName);
                profileImage.setOriImgName(oriImgName);
                profileImage.setImgUrl(imageUrl);
                profileImage.setUser(user);

                return profileImageRepository.save(profileImage);
            } else {
                // 카카오 프로필 이미지가 존재하는 경우 저장
                oriImgName = "kakao_profile_image.jpg";
                savedFileName = fileService.uploadFile(profileImageLocation, oriImgName, imageBytes);
                imageUrl = "/profileImages/" + savedFileName;

                ProfileImage profileImage = new ProfileImage();
                profileImage.setImgName(savedFileName);
                profileImage.setOriImgName(oriImgName);
                profileImage.setImgUrl(imageUrl);
                profileImage.setUser(user);

                return profileImageRepository.save(profileImage);
            }
        } catch (Exception e) {
            throw new FileUploadError(ImageErrorCode.FILE_UPLOAD_ERROR);
        }
    }

    //프로필 이미지 반환 메서드
    @Transactional(readOnly = true)
    @Override
    public String getProfileImage(Long userIdx) {
        ProfileImage profileImage = profileImageRepository.findByUserIdx(userIdx).orElse(null);
        if (profileImage == null || profileImage.getImgUrl() == null || profileImage.getImgUrl().isEmpty()) {
            log.info("사용자 {}에 대한 프로필 이미지가 없으므로 기본 이미지를 반환합니다.", userIdx);
            return "/defaultImages/default.jpg"; // 기본 이미지 URL 설정
        }
        log.info("사용자 {}의 프로필 이미지 URL: {}", userIdx, profileImage.getImgUrl());
        return profileImage.getImgUrl();
    }
}
