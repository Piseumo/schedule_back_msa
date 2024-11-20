package com.example.dailyLog.service;

import com.example.dailyLog.constant.Status;
import com.example.dailyLog.dto.response.FriendListResponseDto;
import com.example.dailyLog.dto.response.UserSearchResponseDto;
import com.example.dailyLog.entity.Friend;
import com.example.dailyLog.entity.User;
import com.example.dailyLog.repository.FriendRepository;
import com.example.dailyLog.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FriendServiceImpl implements FriendService{

    private final FriendRepository friendRepository;
    private final UserRepository userRepository;

    // 친구 요청 보내기
    @Transactional
    @Override
    public void sendFriendRequest(Long requesterId, Long receiverId) {
        if (requesterId.equals(receiverId)) {
            throw new IllegalArgumentException("Cannot send an friend request to oneself.");
        }
        User requester = userRepository.findById(requesterId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid requester ID"));
        User receiver = userRepository.findById(receiverId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid receiver ID"));

        // 중복 요청 방지 로직
        if (friendRepository.existsByRequesterAndReceiver(requester, receiver) ||
            friendRepository.existsByRequesterAndReceiver(receiver, requester)) {
            throw new IllegalStateException("Already sent a friend request to this user.");
        }

        // 새로운 Friend 인스턴스 생성
        Friend friendRequest = new Friend(requester, receiver);
        friendRequest.setStatus(Status.PENDING);
        friendRepository.save(friendRequest);
    }

    //친구 요청 목록 조회
    @Override
    @Transactional
    public List<UserSearchResponseDto> getFriendRequests(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user ID"));

        List<Friend> friendRequests = friendRepository.findByReceiverAndStatus(user, Status.PENDING);

        return friendRequests.stream()
                .map(friend -> {
                    User requester = friend.getRequester();
                    return new UserSearchResponseDto(
                            requester.getIdx(),
                            requester.getUserName(),
                            requester.getProfileImage() != null ? requester.getProfileImage().getImgUrl() : "/images/default.png", // 기본 이미지 경로 설정
                            requester.getEmail()
                    );
                })
                .collect(Collectors.toList());
    }

    // 친구 요청 수락
    @Transactional
    @Override
    public void acceptFriendRequest(Long requesterId, Long receiverId) {
        User requester = userRepository.findById(requesterId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid requester ID"));
        User receiver = userRepository.findById(receiverId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid receiver ID"));

        // 요청자와 수신자 간의 기존 요청을 수락
        Friend friendRequest = friendRepository.findByRequesterAndReceiver(requester, receiver)
                .orElseThrow(() -> new IllegalArgumentException("Invalid friend request"));
        friendRequest.setStatus(Status.ACCEPTED);
        friendRepository.save(friendRequest);

        // 반대 방향의 친구 요청이 이미 있는지 확인
        if (!friendRepository.existsByRequesterAndReceiver(receiver, requester)) {
            Friend reverseFriendRequest = new Friend(receiver, requester);
            reverseFriendRequest.setStatus(Status.ACCEPTED);
            friendRepository.save(reverseFriendRequest);
        }
    }

    // 친구 요청 거절
    @Transactional
    @Override
    public void rejectFriendRequest(Long friendRequestId) {
        Friend friendRequest = friendRepository.findById(friendRequestId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid friend request ID"));
        friendRequest.setStatus(Status.REJECTED);
        friendRepository.save(friendRequest);
    }

    // 친구 목록 조회
    @Transactional
    @Override
    public List<FriendListResponseDto> getFriendsList(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user ID"));

        List<Friend> friends = friendRepository.findByReceiverAndStatus(user, Status.ACCEPTED);

        return friends.stream()
                .map(friend -> {
                    User friendUser = friend.getRequester().getIdx().equals(userId) ? friend.getReceiver() : friend.getRequester();
                    return new FriendListResponseDto(friendUser.getIdx(), friendUser.getUserName());
                })
                .collect(Collectors.toList());
    }

    //친구 검색 기능
    @Transactional
    @Override
    public List<UserSearchResponseDto> searchUsersByUserName(Long userId, String userName) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user ID"));

        // 현재 친구인 사용자 ID 가져오기
        List<Long> friendIds = friendRepository.findFriendsByUser(user).stream()
                .map(friend -> friend.getRequester().getIdx().equals(userId) ? friend.getReceiver().getIdx() : friend.getRequester().getIdx())
                .collect(Collectors.toList());
        friendIds.add(userId); // 자기 자신도 제외하기 위해 추가

        return userRepository.findByUserNameContainingAndIdxNotIn(userName, friendIds).stream()
                .map(foundUser -> new UserSearchResponseDto(
                        foundUser.getIdx(),
                        foundUser.getUserName(),
                        foundUser.getProfileImage() != null ? foundUser.getProfileImage().getImgUrl() : "/images/default.png",
                        foundUser.getEmail()
                ))
                .collect(Collectors.toList());
    }
}

