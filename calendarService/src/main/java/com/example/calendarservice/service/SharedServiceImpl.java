package com.example.calendarservice.service;

import com.example.calendarservice.dto.response.UserSearchResponseDto;
import com.example.calendarservice.entity.Shared;
import com.example.calendarservice.feign.FriendClient;
import com.example.calendarservice.repository.SharedRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SharedServiceImpl implements SharedService{

    private final SharedRepository sharedRepository;
    private final FriendClient friendClient;


    // 쓰레드 첫화면(모든 친구의 공유 컨텐츠 조회)
    @Transactional
    public List<Shared> findAllShared(Long userIdx){

        List<UserSearchResponseDto> friendsList = friendClient.getFriendsList(userIdx);

    }


    // 클릭 했을 때 쓰레드 컨텐츠 개별 화면 조회 (+댓글 조회 메소드 호출)



    // 클릭 했을 때 쓰레드 컨텐츠 개별 화면에서 공유 취소



    // 사용자 개인의 공유 컨텐츠 모아보기


    // 해당 게시글 전체 댓글 조회


    // 댓글 수정


    // 댓글 삭제

}
