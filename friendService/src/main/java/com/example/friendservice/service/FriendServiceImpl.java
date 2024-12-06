package com.example.friendservice.service;

import com.example.friendservice.constant.Status;
import com.example.friendservice.dto.response.FriendListResponseDto;
import com.example.friendservice.dto.response.UserSearchResponseDto;
import com.example.friendservice.entity.Friend;
import com.example.friendservice.entity.User;
import com.example.friendservice.feign.UserFeignClient;
import com.example.friendservice.repository.FriendRepository;
import com.example.friendservice.repository.UserRepository;
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
        Friend friendRequest = Friend.builder()
                .requesterId(requesterId)
                .receiverId(receiverId)
                .status(Status.PENDING).build();

        friendRepository.save(friendRequest);
    }


    //받은 친구 요청 목록 조회
    @Override
    @Transactional
    public List<Long> getFriendRequests(Long userId) {
        List<Long> requesterId = friendRepository.findByReceiverAndStatus(userId, Status.PENDING).stream()
                .map(friend -> friend.getRequesterId())
                .collect(Collectors.toList());


        return userFeignClient.geg(requesterId);
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

        List<Friend> friends = friendRepository.findByReceiverAndStatus(userId, Status.ACCEPTED);

        return friends.stream()
                .map(friend -> {
                    User friendUser = friend.getRequester().getIdx().equals(userId) ? friend.getReceiver() : friend.getRequester();
                    return new FriendListResponseDto(friendUser.getIdx(), friendUser.getUserName());
                })
                .collect(Collectors.toList());
    }



    // 친구 삭제
    @Transactional
    @Override
    public void deleteFriend(Long userId, Long deletedFriendId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user ID"));
        User friend = userRepository.findById(deletedFriendId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid friend ID"));

        friendRepository.deleteByRequesterAndReceiver(user, friend);
        friendRepository.deleteByRequesterAndReceiver(friend, user);
    }
}

