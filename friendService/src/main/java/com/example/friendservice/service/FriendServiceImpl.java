package com.example.friendservice.service;

import com.example.friendservice.constant.Status;
import com.example.friendservice.dto.response.UserSearchResponseDto;
import com.example.friendservice.entity.Friend;
import com.example.friendservice.feign.UserFeignClient;
import com.example.friendservice.repository.FriendRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class FriendServiceImpl implements FriendService{

    private final FriendRepository friendRepository;
    private final UserFeignClient userFeignClient;

    //친구가 아닌 유저 검색
    @Transactional
    @Override
    public List<UserSearchResponseDto> searchUsersByUserName(Long userId, String userName) {
        // 현재 친구인 사용자 ID 가져오기
        List<Long> friendIds = friendRepository.findFriendsByUser(userId).stream()
                .map(friend -> friend.getRequesterId().equals(userId) ? friend.getReceiverId() : friend.getRequesterId())
                .collect(Collectors.toList());
        friendIds.add(userId); // 자기 자신도 제외하기 위해 추가

        return userFeignClient.searchUsers(userName, friendIds);
    }

    // 친구 요청 보내기
    @Transactional
    @Override
    public void sendFriendRequest(Long requesterId, Long receiverId) {
        if (requesterId.equals(receiverId)) {
            throw new IllegalArgumentException("Cannot send an friend request to oneself.");
        }
        // 중복 요청 방지 로직
        if (friendRepository.existsByRequesterIdAndReceiverId(requesterId, receiverId) ||
            friendRepository.existsByRequesterIdAndReceiverId(receiverId, requesterId)) {
            throw new IllegalStateException("Already sent a friend request to this user.");
        }

        // 새로운 Friend 인스턴스 생성
        Friend friendRequest = Friend.builder()
                .requesterId(requesterId)
                .receiverId(receiverId)
                .status(Status.PENDING).build();

        friendRepository.save(friendRequest);
    }

    //받은 친구 요청 목록 조회
    @Override
    @Transactional
    public ResponseEntity<List<UserSearchResponseDto>> getFriendRequests(Long userId) {
        List<Long> requesterId = friendRepository.findByReceiverIdAndStatus(userId, Status.PENDING).stream()
                .map(friend -> friend.getRequesterId())
                .collect(Collectors.toList());

        return userFeignClient.friendRequestList(requesterId);
    }

    // 친구 요청 수락
    @Transactional
    @Override
    public void acceptFriendRequest(Long requesterId, Long receiverId) {
        // 반대 방향의 친구 요청이 이미 있는지 확인
        if (!friendRepository.existsByRequesterIdAndReceiverId(receiverId, requesterId)) {
            Friend reverseFriendRequest = Friend.builder()
                    .requesterId(requesterId)
                    .receiverId(receiverId)
                    .status(Status.ACCEPTED).build();
            friendRepository.save(reverseFriendRequest);
        } // 무슨 로직인지 질문

        // 요청자와 수신자 간의 기존 요청을 수락
        Friend friendRequest = friendRepository.findByRequesterIdAndReceiverId(requesterId, receiverId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid friend request"));
        friendRequest.setStatus(Status.ACCEPTED);
        friendRepository.save(friendRequest); // 무슨 로직인지 질문

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
    public List<UserSearchResponseDto> getFriendsList(Long userId) {
        List<Friend> friends = friendRepository.findByReceiverIdAndStatus(userId, Status.ACCEPTED);

        List<Long> userIds = friends.stream()
                .flatMap(friend -> Stream.of(friend.getRequesterId(), friend.getReceiverId()))
                .distinct()
                .collect(Collectors.toList());

        return userFeignClient.getFriendsList(userIds);
    }



    // 친구 삭제
    @Transactional
    @Override
    public void deleteFriend(Long userId, Long deletedFriendId) {

        friendRepository.deleteByRequesterIdAndReceiverId(userId, deletedFriendId);
        friendRepository.deleteByRequesterIdAndReceiverId(deletedFriendId, userId);
    }
}

