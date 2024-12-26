package com.example.calendarservice.controller;

import com.example.calendarservice.dto.request.CommentsRequestInsertDto;
import com.example.calendarservice.dto.request.CommentsRequestUpdateDto;
import com.example.calendarservice.dto.response.CommentsResponseAllDto;
import com.example.calendarservice.dto.response.SharedContentDto;
import com.example.calendarservice.service.SharedService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/shared")
@RequiredArgsConstructor
@CrossOrigin
public class SharedController {


    private final SharedService sharedService;


    // 쓰레드 첫화면(모든 친구의 공유 컨텐츠 조회)xxx
    @GetMapping("/all/{userIdx}")
    public ResponseEntity<List<SharedContentDto>> findAllShared(@PathVariable(value = "userIdx") Long userIdx) {
        List<SharedContentDto> sharedContents = sharedService.findAllShared(userIdx);
        return ResponseEntity.ok(sharedContents);
    }


    // 클릭 했을 때 쓰레드 컨텐츠 개별 화면 조회 (1개)
    @GetMapping("/one/{sharedIdx}")
    public ResponseEntity<SharedContentDto> findOneShared(@PathVariable(value = "sharedIdx") Long sharedIdx){
        SharedContentDto sharedContentDto = sharedService.findOneShared(sharedIdx);
        return ResponseEntity.ok(sharedContentDto);
    }


    // 클릭 했을 때 쓰레드 컨텐츠 개별 화면에서 공유 취소
    @PostMapping("/delete/shared/{sharedIdx}")
    public ResponseEntity<Boolean> deleteShared(@PathVariable(value = "sharedIdx") Long sharedIdx){
        sharedService.deleteShared(sharedIdx);
        return ResponseEntity.ok(true);
    }


    // 사용자 개인의 공유 컨텐츠 모아보기
    @GetMapping("/user/{userIdx}")
    public ResponseEntity<List<SharedContentDto>> findSharedByUser(@PathVariable(value = "userIdx") Long userIdx){
        List<SharedContentDto> sharedContentDtos = sharedService.findSharedByUser(userIdx);
        return ResponseEntity.ok(sharedContentDtos);
    }


    // 댓글 입력
    @PostMapping("/create/comments")
    public ResponseEntity<Boolean> saveComments(@RequestBody CommentsRequestInsertDto commentsRequestInsertDto){
        sharedService.saveComments(commentsRequestInsertDto);
        return ResponseEntity.ok(true);
    }


    // 해당 게시글 전체 댓글 조회
    @GetMapping("/all/comments/{sharedIdx}")
    public ResponseEntity<List<CommentsResponseAllDto>> findAllComments(@PathVariable(value = "sharedIdx") Long sharedIdx){
        List<CommentsResponseAllDto> commentsResponseAllDto = sharedService.findAllComments(sharedIdx);
        return ResponseEntity.ok(commentsResponseAllDto);
    }


    // 댓글 수정
    @PostMapping("/update/comments")
    public ResponseEntity<Boolean> updateComments(@RequestBody CommentsRequestUpdateDto commentsRequestUpdateDto){
        sharedService.updateComments(commentsRequestUpdateDto);
        return ResponseEntity.ok(true);
    }


    // 댓글 삭제
    @DeleteMapping("/delete/{commentsIdx}")
    public ResponseEntity<Boolean> deleteComments(@PathVariable(value = "commentsIdx") Long commentsIdx){
        sharedService.deleteComments(commentsIdx);
        return ResponseEntity.ok(true);
    }
}








