package com.example.calendarservice.service;

import com.example.calendarservice.constant.Share;
import com.example.calendarservice.dto.request.*;
import com.example.calendarservice.dto.response.CommentsResponseAllDto;
import com.example.calendarservice.dto.response.SharedContentDto;
import com.example.calendarservice.dto.response.UserSearchResponseDto;
import com.example.calendarservice.entity.Comments;
import com.example.calendarservice.entity.Diary;
import com.example.calendarservice.entity.Schedule;
import com.example.calendarservice.entity.Shared;
import com.example.calendarservice.feign.FriendClient;
import com.example.calendarservice.repository.CommentsRepository;
import com.example.calendarservice.repository.DiaryRepository;
import com.example.calendarservice.repository.ScheduleRepository;
import com.example.calendarservice.repository.SharedRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class SharedServiceImpl implements SharedService{

    private final SharedRepository sharedRepository;
    private final FriendClient friendClient;
    private final ScheduleRepository scheduleRepository;
    private final DiaryRepository diaryRepository;
    private final CommentsRepository commentsRepository;
    private final DiaryService diaryService;
    private final ScheduleService scheduleService;



    // 공유 내역 테이블 생성
    @Transactional
    public void saveShared(SharedRequestInsertDto sharedRequestInsertDto){


        Shared createShared = Shared.builder()
                .scheduleIdx(sharedRequestInsertDto.getScheduleIdx())
                .diaryIdx(sharedRequestInsertDto.getDiaryIdx())
                .friendIdx(sharedRequestInsertDto.getFriendIdx())
                .shareDateTime(sharedRequestInsertDto.getShareDateTime())
                .build();
        sharedRepository.save(createShared);
    }


    // 공유 친구, 시간 수정
    @Transactional
    public void updateShared(SharedRequestUpdateDto sharedRequestUpdateDto){

        if (sharedRequestUpdateDto.getScheduleIdx() != null && sharedRequestUpdateDto.getDiaryIdx() == null){
            sharedRequestUpdateDto.setSharedIdx(sharedRepository.findSharedIdxByScheduleIdx(sharedRequestUpdateDto.getScheduleIdx()));
        } else if (sharedRequestUpdateDto.getScheduleIdx() == null && sharedRequestUpdateDto.getDiaryIdx() != null) {
            sharedRequestUpdateDto.setSharedIdx(sharedRepository.findSharedIdxByDiaryIdx(sharedRequestUpdateDto.getDiaryIdx()));
        } else {
            throw new IllegalArgumentException("일정, 일기 Idx가 모두 존재하지 않습니다.");
        }

        Shared updateShared = sharedRepository.findById(sharedRequestUpdateDto.getSharedIdx())
                .orElseThrow(() -> new IllegalArgumentException("공유 내역이 존재하지 않습니다."));

        if (sharedRequestUpdateDto.getFriendIdx() != null){
            updateShared.setFriendIdx(sharedRequestUpdateDto.getFriendIdx());
        }
        if (sharedRequestUpdateDto.getShareDateTime() != null){
            updateShared.setShareDateTime(sharedRequestUpdateDto.getShareDateTime());
        }
        sharedRepository.save(updateShared);
    }


    // 쓰레드 첫화면(모든 친구의 공유 컨텐츠 조회)
    @Transactional
    public List<SharedContentDto> findAllShared(Long userIdx){

        List<Long> friendIds = friendClient.getFriendsList(userIdx).stream()
                .map(UserSearchResponseDto::getUserId)
                .collect(Collectors.toList());

        List<Shared> allShared = sharedRepository.findAllSharedWithAllAndFriends(friendIds);
        List<Shared> chooseShared = sharedRepository.findAllSharedWithChoose(userIdx);

        List<Shared> sharedList = new ArrayList<>();
        sharedList.addAll(allShared);
        sharedList.addAll(chooseShared);

        List<SharedContentDto> result = sharedList.stream()
                .map(shared -> {
                    if (shared.getScheduleIdx() != null) {
                        return scheduleRepository.findById(shared.getScheduleIdx())
                                .map(schedule -> SharedContentDto.builder()
                                        .sharedIdx(shared.getSharedIdx())
                                        .shareDate(shared.getShareDateTime())
                                        .type("SCHEDULE")
                                        .title(schedule.getTitle())
                                        .content(schedule.getContent())
                                        .start(schedule.getStart())
                                        .end(schedule.getEnd())
                                        .location(schedule.getLocation())
                                        .repeatType(schedule.getRepeatType())
                                        .repeatEndDate(schedule.getRepeatEndDate())
                                        .scheduleImages(schedule.getScheduleImages())
                                        .share(schedule.getShare())
                                        .build())
                                .orElse(null);
                    } else if (shared.getDiaryIdx() != null) {
                        return diaryRepository.findById(shared.getDiaryIdx())
                                .map(diary -> SharedContentDto.builder()
                                        .sharedIdx(shared.getSharedIdx())
                                        .shareDate(shared.getShareDateTime())
                                        .type("DIARY")
                                        .title(diary.getTitle())
                                        .content(diary.getContent())
                                        .date(diary.getDate())
                                        .category(diary.getCategory().name())
                                        .diaryImages(diary.getDiaryImages())
                                        .share(diary.getShare())
                                        .build())
                                .orElse(null);
                    }
                    return null;
                })
                .filter(Objects::nonNull)
                .sorted(Comparator.comparing(SharedContentDto::getShareDate).reversed())
                .collect(Collectors.toList());

        return result;
    }



    // 클릭 했을 때 쓰레드 컨텐츠 개별 화면 조회 (1개)
    @Transactional
    public SharedContentDto findOneShared(Long sharedIdx){

        Shared shared = sharedRepository.findById(sharedIdx)
                .orElseThrow(() -> new IllegalArgumentException("Shared 정보가 존재하지 않습니다."));

        if (shared.getScheduleIdx() != null) {
            Schedule schedule = scheduleRepository.findById(shared.getScheduleIdx())
                    .orElseThrow(() -> new IllegalArgumentException("Schedule 정보가 존재하지 않습니다."));

            return SharedContentDto.builder()
                    .sharedIdx(shared.getSharedIdx())
                    .shareDate(shared.getShareDateTime())
                    .type("SCHEDULE")
                    .title(schedule.getTitle())
                    .content(schedule.getContent())
                    .start(schedule.getStart())
                    .end(schedule.getEnd())
                    .location(schedule.getLocation())
                    .repeatType(schedule.getRepeatType())
                    .repeatEndDate(schedule.getRepeatEndDate())
                    .scheduleImages(schedule.getScheduleImages())
                    .share(schedule.getShare())
                    .build();

        } else if (shared.getDiaryIdx() != null) {
            Diary diary = diaryRepository.findById(shared.getDiaryIdx())
                    .orElseThrow(() -> new IllegalArgumentException("Diary 정보가 존재하지 않습니다."));

            return SharedContentDto.builder()
                    .sharedIdx(shared.getSharedIdx())
                    .shareDate(shared.getShareDateTime())
                    .type("DIARY")
                    .title(diary.getTitle())
                    .content(diary.getContent())
                    .date(diary.getDate())
                    .category(diary.getCategory().name())
                    .diaryImages(diary.getDiaryImages())
                    .share(diary.getShare())
                    .build();
        } else {
            throw new IllegalArgumentException("Shared 정보에 Schedule 또는 Diary가 없습니다.");
        }
    }



    // 클릭 했을 때 쓰레드 컨텐츠 개별 화면에서 공유 취소
    @Transactional
    public void deleteShared(Long sharedIdx){

        Shared shared = sharedRepository.findById(sharedIdx)
                .orElseThrow(() -> new IllegalArgumentException("공유 일정 or 일기가 존재하지 않습니다."));

        if (shared.getScheduleIdx() != null && shared.getDiaryIdx() == null){
            ScheduleRequestUpdateDto scheduleRequestUpdateDto = ScheduleRequestUpdateDto.builder()
                    .idx(shared.getScheduleIdx())
                    .share(Share.NONE)
                    .build();
            scheduleService.updateSchedule(scheduleRequestUpdateDto, null);
        } else if (shared.getScheduleIdx() == null && shared.getDiaryIdx() != null) {
            DiaryRequestUpdateDto diaryRequestUpdateDto = DiaryRequestUpdateDto.builder()
                    .idx(shared.getDiaryIdx())
                    .share(Share.NONE)
                    .build();
            diaryService.updateDiary(diaryRequestUpdateDto, null);
        } else {
            throw new IllegalArgumentException("일정, 일기 Idx가 모두 존재하지 않습니다.");
        }
        sharedRepository.delete(shared);
    }



    // 일정이나 일기에서 공유 취소
    @Transactional
    public void deleteContentShared(SharedRequestUpdateDto sharedRequestUpdateDto){

        if (sharedRequestUpdateDto.getScheduleIdx() != null && sharedRequestUpdateDto.getDiaryIdx() == null){
            sharedRequestUpdateDto.setSharedIdx(sharedRepository.findSharedIdxByScheduleIdx(sharedRequestUpdateDto.getScheduleIdx()));
        } else if (sharedRequestUpdateDto.getScheduleIdx() == null && sharedRequestUpdateDto.getDiaryIdx() != null) {
            sharedRequestUpdateDto.setSharedIdx(sharedRepository.findSharedIdxByDiaryIdx(sharedRequestUpdateDto.getDiaryIdx()));
        } else {
            throw new IllegalArgumentException("일정, 일기 Idx가 모두 존재하지 않습니다.");
        }

        Shared shared = sharedRepository.findById(sharedRequestUpdateDto.getSharedIdx())
                .orElseThrow(() -> new IllegalArgumentException("공유 일정 or 일기가 존재하지 않습니다."));
        sharedRepository.delete(shared);
    }



    // 사용자 개인의 공유 컨텐츠 모아보기
    @Transactional
    public List<SharedContentDto> findSharedByUser(Long userIdx){
        List<Shared> allSchedules = sharedRepository.findSharedSchedulesByUser(userIdx);
        List<Shared> allDiaries = sharedRepository.findSharedDiariesByUser(userIdx);

        return Stream.concat(
                        allSchedules.stream().map(shared -> {
                            Schedule schedule = scheduleRepository.findById(shared.getScheduleIdx()).orElse(null);
                            return SharedContentDto.builder()
                                    .sharedIdx(shared.getSharedIdx())
                                    .type("SCHEDULE")
                                    .title(schedule.getTitle())
                                    .content(schedule.getContent())
                                    .shareDate(shared.getShareDateTime())
                                    .start(schedule.getStart())
                                    .end(schedule.getEnd())
                                    .location(schedule.getLocation())
                                    .scheduleImages(schedule.getScheduleImages())
                                    .build();
                        }),
                        allDiaries.stream().map(shared -> {
                            Diary diary = diaryRepository.findById(shared.getDiaryIdx()).orElse(null);
                            return SharedContentDto.builder()
                                    .sharedIdx(shared.getSharedIdx())
                                    .type("DIARY")
                                    .title(diary.getTitle())
                                    .content(diary.getContent())
                                    .shareDate(shared.getShareDateTime())
                                    .date(diary.getDate())
                                    .category(diary.getCategory().name())
                                    .diaryImages(diary.getDiaryImages())
                                    .build();
                        })
                ).sorted(Comparator.comparing(SharedContentDto::getShareDate).reversed())
                .collect(Collectors.toList());
    }



    // 댓글 입력
    @Transactional
    public void saveComments(CommentsRequestInsertDto commentsRequestInsertDto){
        Shared sharedIdx = sharedRepository.findById(commentsRequestInsertDto.getSharedIdx())
                .orElseThrow(() -> new IllegalArgumentException("공유 컨텐츠(일정 or 일기)가 존재하지 않습니다."));

        Comments createComments = Comments.builder()
                .userIdx(commentsRequestInsertDto.getUserIdx())
                .sharedIdx(sharedIdx.getSharedIdx())
                .dateTime(commentsRequestInsertDto.getDateTime())
                .content(commentsRequestInsertDto.getContent())
                .build();
        commentsRepository.save(createComments);
    }



    // 해당 게시글 전체 댓글 조회
    @Transactional
    public List<CommentsResponseAllDto> findAllComments(Long sharedIdx){

        Shared shared = sharedRepository.findById(sharedIdx)
                .orElseThrow(() -> new IllegalArgumentException("Shared 정보가 존재하지 않습니다."));

        List<CommentsResponseAllDto> commentsList = new ArrayList<>();

        if (shared.getScheduleIdx() != null) {
            Schedule schedule = scheduleRepository.findById(shared.getScheduleIdx())
                    .orElseThrow(() -> new IllegalArgumentException("Schedule 정보가 존재하지 않습니다."));

            List<Comments> scheduleComments = commentsRepository.findByScheduleIdx(schedule.getIdx());
            commentsList.addAll(scheduleComments.stream()
                    .map(comment -> CommentsResponseAllDto.builder()
                            .commentsIdx(comment.getCommentsIdx())
                            .userIdx(comment.getUserIdx())
                            .sharedIdx(comment.getSharedIdx())
                            .dateTime(comment.getDateTime())
                            .content(comment.getContent())
                            .build())
                    .toList());
        }

        if (shared.getDiaryIdx() != null) {
            Diary diary = diaryRepository.findById(shared.getDiaryIdx())
                    .orElseThrow(() -> new IllegalArgumentException("Diary 정보가 존재하지 않습니다."));

            List<Comments> diaryComments = commentsRepository.findByDiaryIdx(diary.getIdx());
            commentsList.addAll(diaryComments.stream()
                    .map(comment -> CommentsResponseAllDto.builder()
                            .commentsIdx(comment.getCommentsIdx())
                            .userIdx(comment.getUserIdx())
                            .sharedIdx(comment.getSharedIdx())
                            .dateTime(comment.getDateTime())
                            .content(comment.getContent())
                            .build())
                    .toList());
        }
        return commentsList;
    }



    // 댓글 수정
    @Transactional
    public void updateComments(CommentsRequestUpdateDto commentsRequestUpdateDto){
        Comments updateComments = commentsRepository.findById(commentsRequestUpdateDto.getCommentsIdx())
                .orElseThrow(() -> new IllegalArgumentException("해당 댓글이 존재하지 않습니다."));

        if (commentsRequestUpdateDto.getContent() != null){
            updateComments.setContent(commentsRequestUpdateDto.getContent());
        }
        commentsRepository.save(updateComments);
    }



    // 댓글 삭제
    @Transactional
    public void deleteComments(Long commentsIdx){
        Comments comments = commentsRepository.findById(commentsIdx)
                .orElseThrow(() -> new IllegalArgumentException("해당하는 댓글이 존재하지 않습니다."));
        commentsRepository.delete(comments);
    }
}
