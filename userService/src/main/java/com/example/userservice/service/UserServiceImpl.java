package com.example.userservice.service;

import com.example.userservice.constant.Provider;
import com.example.userservice.dto.request.UserRequestInsertDto;
import com.example.userservice.dto.request.UserRequestUpdateDto;
import com.example.userservice.dto.response.UserResponseDto;
import com.example.userservice.dto.response.UserSearchResponseDto;
import com.example.userservice.entity.ProfileImage;
import com.example.userservice.entity.User;
import com.example.userservice.exception.commonException.CommonErrorCode;
import com.example.userservice.exception.commonException.error.BizException;
import com.example.userservice.exception.loginException.DuplicateEmailException;
import com.example.userservice.exception.loginException.LoginErrorCode;
import com.example.userservice.exception.loginException.UserPKException;
import com.example.userservice.exception.userException.UserErrorCode;
import com.example.userservice.exception.userException.UserNotFoundException;
import com.example.userservice.feign.CalendarClient;
import com.example.userservice.repository.ProfileImageRepository;
import com.example.userservice.repository.UserRepository;
import com.example.userservice.security.providers.JwtTokenProvider;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Validated
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ProfileImageRepository profileImageRepository;
    private final PasswordEncoder passwordEncoder;
    private final ImageService imageService;
    private final EntityManager entityManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final CalendarClient calendarClient;

    // 회원가입
    @Override
    @Transactional
    public ResponseEntity<UserResponseDto> createUser(@Valid UserRequestInsertDto userRequestInsertDto) {
        try {

            User user = User.builder()
                    .email(userRequestInsertDto.getEmail())
                    .password(passwordEncoder.encode(userRequestInsertDto.getPassword()))
                    .userName(userRequestInsertDto.getUserName())
                    .provider(Provider.LOCAL)
                    .build();

            userRepository.save(user);

            calendarClient.createCalendar(user.getIdx());

            return ResponseEntity.ok(new UserResponseDto(user.getIdx()));

        } catch (DuplicateKeyException e) {
            throw new DuplicateEmailException(LoginErrorCode.DUPLICATE_EMAIL);
        } // catch (Exception e) {
//            throw new MyInternalServerError(CommonErrorCode.INTERNAL_SERVER_ERROR);
//        }
    }

    // 회원 찾기
    @Override
    public User findUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new BizException(CommonErrorCode.NOT_FOUND));
    }

    //유저 검색(feign)
    @Override
    @Transactional
    public List<UserSearchResponseDto> searchUserByUserName(String userName, List<Long> friendIds) {

        return userRepository.findByUserNameContainingAndIdxNotIn(userName, friendIds).stream()
                .map(foundUser -> new UserSearchResponseDto(
                        foundUser.getIdx(),
                        foundUser.getUserName(),
                        foundUser.getProfileImage() != null ? foundUser.getProfileImage().getImgUrl() : "/images/default.png"
                ))
                .collect(Collectors.toList());
    }

    //유저 요청 목록 조회(feign)
    @Override
    @Transactional                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                
    public List<UserSearchResponseDto> searchRequester(List<Long> requesterId) {

        return userRepository.findAllById(requesterId).stream()
                .map(foundUser -> new UserSearchResponseDto(
                        foundUser.getIdx(),
                        foundUser.getUserName(),
                        foundUser.getProfileImage() != null ? foundUser.getProfileImage().getImgUrl() : "/images/default.png"
                ))
                .collect(Collectors.toList());
    }

    // 친구 목록 조회(feign)
    @Override
    @Transactional
    public List<UserSearchResponseDto> searchFriend(List<Long> friendsId) {

        return userRepository.findAllById(friendsId).stream()
                .map(foundUser -> new UserSearchResponseDto(
                        foundUser.getIdx(),
                        foundUser.getUserName(),
                        foundUser.getProfileImage() != null ? foundUser.getProfileImage().getImgUrl() : "/images/default.png"
                ))
                .collect(Collectors.toList());
    }

    // 닉네임 수정
    @Override
    @Transactional
    public void updateUserName(@Valid UserRequestUpdateDto userRequestUpdateDto) {
        try {
            User updateUser = userRepository.findById(userRequestUpdateDto.getIdx())
                    .orElseThrow(() -> new BizException(CommonErrorCode.NOT_FOUND));

            if (userRequestUpdateDto.getUserName() != null) {
                updateUser.setUserName(userRequestUpdateDto.getUserName());
            }

            userRepository.save(updateUser);

        } catch (Exception e) {
            throw new RuntimeException("닉네임 업데이트 중 오류 발생", e);
        }
    }

    // 프로필 사진 수정
    @Override
    @Transactional
    public void updateProfileImage(Long idx, MultipartFile imageFile) {

            User updateUser = userRepository.findById(idx)
                    .orElseThrow(() -> new UserNotFoundException(UserErrorCode.USER_NOT_FOUND));
            // 기존 이미지 삭제 로직 추가
        try {
            ProfileImage oldProfileImage = updateUser.getProfileImage();
            if (oldProfileImage != null) {
                updateUser.setProfileImage(null);
                profileImageRepository.delete(oldProfileImage);
                entityManager.flush();
            }

            if (imageFile != null) {
                ProfileImage profileImage = imageService.saveProfileImage(imageFile, updateUser);
                profileImage.setUser(updateUser);
                updateUser.setProfileImage(profileImage);
            }
        } catch (Exception e) {
            throw new RuntimeException("프로필 이미지 업데이트 중 오류 발생", e);
        }
    }


    @Transactional
    @Override
    public void deleteUser(String email, String authToken) {
        String userIdFromToken = jwtTokenProvider.getUserPk(authToken);

        if (!userIdFromToken.equals(email)) {
            throw new UserPKException(LoginErrorCode.USER_PK);
        }

        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new BizException(CommonErrorCode.NOT_FOUND)
        );

        userRepository.deleteById(user.getIdx());
    }


}
