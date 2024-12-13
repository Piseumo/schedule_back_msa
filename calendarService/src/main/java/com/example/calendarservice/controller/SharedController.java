package com.example.calendarservice.controller;

import com.example.calendarservice.service.SharedService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/shared")
@RequiredArgsConstructor
public class SharedController {

    private final SharedService sharedService;


    // 쓰레드 첫화면(모든 친구의 공유 컨텐츠 조회)



    // 클릭 했을 때 쓰레드 컨텐츠 개별 화면 조회 (+댓글 조회 메소드 호출)



    // 클릭 했을 때 쓰레드 컨텐츠 개별 화면에서 공유 취소



    // 사용자 개인의 공유 컨텐츠 모아보기


    // 해당 게시글 전체 댓글 조회


    // 댓글 수정


    // 댓글 삭제
}
