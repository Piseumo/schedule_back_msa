package com.example.calendarservice.service;

import com.example.calendarservice.constant.Share;
import com.example.calendarservice.dto.request.*;
import com.example.calendarservice.dto.response.CommentsResponseAllDto;
import com.example.calendarservice.dto.response.SharedContentDto;
import com.example.calendarservice.dto.response.UserSearchResponseDto;
import com.example.calendarservice.entity.*;
import com.example.calendarservice.feign.FriendClient;
import com.example.calendarservice.repository.CommentsRepository;
import com.example.calendarservice.repository.DiaryRepository;
import com.example.calendarservice.repository.ScheduleRepository;
import com.example.calendarservice.repository.SharedRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
        List<Long> sharedIdxList;

        if (sharedRequestUpdateDto.getScheduleIdx() != null && sharedRequestUpdateDto.getDiaryIdx() == null) {
            sharedIdxList = sharedRepository.findSharedIdxByScheduleIdx(sharedRequestUpdateDto.getScheduleIdx());
        } else if (sharedRequestUpdateDto.getScheduleIdx() == null && sharedRequestUpdateDto.getDiaryIdx() != null) {
            sharedIdxList = sharedRepository.findSharedIdxByDiaryIdx(sharedRequestUpdateDto.getDiaryIdx());
        } else {
            throw new IllegalArgumentException("일정, 일기 Idx가 모두 존재하지 않습니다.");
        }

        if (sharedIdxList == null || sharedIdxList.isEmpty()) {
            SharedRequestInsertDto insertDto = SharedRequestInsertDto.builder()
                    .scheduleIdx(sharedRequestUpdateDto.getScheduleIdx())
                    .diaryIdx(sharedRequestUpdateDto.getDiaryIdx())
                    .friendIdx(sharedRequestUpdateDto.getFriendIdx())
                    .shareDateTime(LocalDateTime.now())
                    .build();

            saveShared(insertDto);
            return;
        }

        Long sharedIdx = sharedIdxList.get(0);
        sharedRequestUpdateDto.setSharedIdx(sharedIdx);

        Shared updateShared = sharedRepository.findById(sharedRequestUpdateDto.getSharedIdx())
                .orElseThrow(() -> new IllegalArgumentException("공유 내역이 존재하지 않습니다."));

        if (!sharedRequestUpdateDto.getFriendIdx().equals(updateShared.getFriendIdx())) {
            Shared newShared = Shared.builder()
                    .scheduleIdx(updateShared.getScheduleIdx())
                    .diaryIdx(updateShared.getDiaryIdx())
                    .friendIdx(sharedRequestUpdateDto.getFriendIdx())
                    .shareDateTime(sharedRequestUpdateDto.getShareDateTime() != null
                            ? sharedRequestUpdateDto.getShareDateTime()
                            : LocalDateTime.now())
                    .build();

            sharedRepository.save(newShared);
        } else {
            if (sharedRequestUpdateDto.getShareDateTime() != null) {
                updateShared.setShareDateTime(sharedRequestUpdateDto.getShareDateTime());
            }
            sharedRepository.save(updateShared);
        }
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
                        List<String> imageUrls = scheduleRepository.findById(shared.getScheduleIdx())
                                .stream().flatMap(schedule -> schedule.getScheduleImages().stream())
                                .map(ScheduleImage::getImgUrl)
                                .collect(Collectors.toList());

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
                                        .scheduleImages(imageUrls)
                                        .share(schedule.getShare())
                                        .build())
                                .orElse(null);
                    } else if (shared.getDiaryIdx() != null) {
                        List<String> imageUrls = diaryRepository.findById(shared.getDiaryIdx())
                                .stream().flatMap(diary -> diary.getDiaryImages().stream())
                                .map(DiaryImage::getImgUrl)
                                .collect(Collectors.toList());

                        return diaryRepository.findById(shared.getDiaryIdx())
                                .map(diary -> SharedContentDto.builder()
                                        .sharedIdx(shared.getSharedIdx())
                                        .shareDate(shared.getShareDateTime())
                                        .type("DIARY")
                                        .title(diary.getTitle())
                                        .content(diary.getContent())
                                        .date(diary.getDate())
                                        .category(diary.getCategory().name())
                                        .diaryImages(imageUrls)
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

            List<String> imageUrls = scheduleRepository.findById(shared.getScheduleIdx())
                    .stream().flatMap(schedule2 -> schedule2.getScheduleImages().stream())
                    .map(ScheduleImage::getImgUrl)
                    .collect(Collectors.toList());

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
                    .scheduleImages(imageUrls)
                    .share(schedule.getShare())
                    .build();

        } else if (shared.getDiaryIdx() != null) {
            Diary diary = diaryRepository.findById(shared.getDiaryIdx())
                    .orElseThrow(() -> new IllegalArgumentException("Diary 정보가 존재하지 않습니다."));

            List<String> imageUrls = diaryRepository.findById(shared.getDiaryIdx())
                    .stream().flatMap(diary2 -> diary2.getDiaryImages().stream())
                    .map(DiaryImage::getImgUrl)
                    .collect(Collectors.toList());

            return SharedContentDto.builder()
                    .sharedIdx(shared.getSharedIdx())
                    .shareDate(shared.getShareDateTime())
                    .type("DIARY")
                    .title(diary.getTitle())
                    .content(diary.getContent())
                    .date(diary.getDate())
                    .category(diary.getCategory().name())
                    .diaryImages(imageUrls)
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
            Schedule updateSchedule = scheduleRepository.findById(scheduleRequestUpdateDto.getIdx())
                    .orElseThrow(() -> new IllegalArgumentException("일정이 존재하지 않습니다."));
            updateSchedule.setShare(scheduleRequestUpdateDto.getShare());
            scheduleRepository.save(updateSchedule);
        } else if (shared.getScheduleIdx() == null && shared.getDiaryIdx() != null) {
            DiaryRequestUpdateDto diaryRequestUpdateDto = DiaryRequestUpdateDto.builder()
                    .idx(shared.getDiaryIdx())
                    .share(Share.NONE)
                    .build();
            Diary updateDiary = diaryRepository.findById(diaryRequestUpdateDto.getIdx())
                    .orElseThrow(() -> new IllegalArgumentException("일기가 존재하지 않습니다."));
            updateDiary.setShare(diaryRequestUpdateDto.getShare());
            diaryRepository.save(updateDiary);
        } else {
            throw new IllegalArgumentException("일정, 일기 Idx가 모두 존재하지 않습니다.");
        }
        sharedRepository.delete(shared);
    }



    // 일정이나 일기에서 공유 취소
    @Transactional
    public void deleteContentShared(SharedRequestUpdateDto sharedRequestUpdateDto) {
        List<Long> sharedIdxList;

        // 공유 데이터 ID 목록 조회
        if (sharedRequestUpdateDto.getScheduleIdx() != null && sharedRequestUpdateDto.getDiaryIdx() == null) {
            sharedIdxList = sharedRepository.findSharedIdxByScheduleIdx(sharedRequestUpdateDto.getScheduleIdx());
        } else if (sharedRequestUpdateDto.getScheduleIdx() == null && sharedRequestUpdateDto.getDiaryIdx() != null) {
            sharedIdxList = sharedRepository.findSharedIdxByDiaryIdx(sharedRequestUpdateDto.getDiaryIdx());
        } else {
            throw new IllegalArgumentException("일정, 일기 Idx가 모두 존재하지 않습니다.");
        }

        // 공유 데이터가 없는 경우 처리
        if (sharedIdxList == null || sharedIdxList.isEmpty()) {
            throw new IllegalArgumentException("삭제할 공유 데이터가 없습니다.");
        }

        // 공유 데이터 삭제
        for (Long sharedIdx : sharedIdxList) {
            Shared shared = sharedRepository.findById(sharedIdx)
                    .orElseThrow(() -> new IllegalArgumentException("공유 일정 or 일기가 존재하지 않습니다. sharedIdx=" + sharedIdx));
            sharedRepository.delete(shared);
        }
    }



    // 사용자 개인의 공유 컨텐츠 모아보기
    @Transactional
    public List<SharedContentDto> findSharedByUser(Long userIdx){
        List<Shared> allSchedules = sharedRepository.findSharedSchedulesByUser(userIdx);
        List<Shared> allDiaries = sharedRepository.findSharedDiariesByUser(userIdx);

        return Stream.concat(
                        allSchedules.stream().map(shared -> {
                            Schedule schedule = scheduleRepository.findById(shared.getScheduleIdx()).orElse(null);

                            List<String> imageUrls = scheduleRepository.findById(shared.getScheduleIdx())
                                    .stream().flatMap(schedule2 -> schedule2.getScheduleImages().stream())
                                    .map(ScheduleImage::getImgUrl)
                                    .collect(Collectors.toList());

                            return SharedContentDto.builder()
                                    .sharedIdx(shared.getSharedIdx())
                                    .type("SCHEDULE")
                                    .title(schedule.getTitle())
                                    .content(schedule.getContent())
                                    .shareDate(shared.getShareDateTime())
                                    .start(schedule.getStart())
                                    .end(schedule.getEnd())
                                    .location(schedule.getLocation())
                                    .scheduleImages(imageUrls)
                                    .build();
                        }),
                        allDiaries.stream().map(shared -> {
                            Diary diary = diaryRepository.findById(shared.getDiaryIdx()).orElse(null);

                            List<String> imageUrls = diaryRepository.findById(shared.getDiaryIdx())
                                    .stream().flatMap(diary2 -> diary2.getDiaryImages().stream())
                                    .map(DiaryImage::getImgUrl)
                                    .collect(Collectors.toList());

                            return SharedContentDto.builder()
                                    .sharedIdx(shared.getSharedIdx())
                                    .type("DIARY")
                                    .title(diary.getTitle())
                                    .content(diary.getContent())
                                    .shareDate(shared.getShareDateTime())
                                    .date(diary.getDate())
                                    .category(diary.getCategory().name())
                                    .diaryImages(imageUrls)
                                    .build();
                        })
                ).sorted(Comparator.comparing(SharedContentDto::getShareDate).reversed())
                .collect(Collectors.toList());
    }



    // 댓글 입력
    @Transactional
    public void saveComments(CommentsRequestInsertDto commentsRequestInsertDto){
        if ((commentsRequestInsertDto.getScheduleIdx() == null && commentsRequestInsertDto.getDiaryIdx() == null) ||
                (commentsRequestInsertDto.getScheduleIdx() != null && commentsRequestInsertDto.getDiaryIdx() != null)) {
            throw new IllegalArgumentException("일정 또는 일기 중 하나의 ID만 제공되어야 합니다.");
        }

        Comments createComments = Comments.builder()
                .userIdx(commentsRequestInsertDto.getUserIdx())
                .scheduleIdx(commentsRequestInsertDto.getScheduleIdx())
                .diaryIdx(commentsRequestInsertDto.getDiaryIdx())
                .dateTime(LocalDateTime.now())
                .content(commentsRequestInsertDto.getContent())
                .build();
        commentsRepository.save(createComments);
    }



    // 해당 게시글 전체 댓글 조회
    @Transactional
    public List<CommentsResponseAllDto> findAllComments(String type, Long mixedIdx){

        List<CommentsResponseAllDto> commentsList = new ArrayList<>();

        if (type == "SCHEDULE"){
            Schedule schedule = scheduleRepository.findById(mixedIdx)
                    .orElseThrow(() -> new IllegalArgumentException("해당 일정이 존재하지 않습니다."));
            if (schedule.getIdx() != null){
                List<Comments> scheduleComments = commentsRepository.findByScheduleIdx(schedule.getIdx());
                commentsList.addAll(scheduleComments.stream()
                        .map(comment -> CommentsResponseAllDto.builder()
                                .commentsIdx(comment.getCommentsIdx())
                                .userIdx(comment.getUserIdx())
                                .scheduleIdx(comment.getScheduleIdx())
                                .diaryIdx(comment.getDiaryIdx())
                                .dateTime(comment.getDateTime())
                                .content(comment.getContent())
                                .build())
                        .toList());
            }

        }else {
            Diary diary = diaryRepository.findById(mixedIdx)
                    .orElseThrow(() -> new IllegalArgumentException("해당 일기가 존재하지 않습니다."));
            if (diary.getIdx() != null){
                List<Comments> diaryComments = commentsRepository.findByDiaryIdx(diary.getIdx());
                commentsList.addAll(diaryComments.stream()
                        .map(comment -> CommentsResponseAllDto.builder()
                                .commentsIdx(comment.getCommentsIdx())
                                .userIdx(comment.getUserIdx())
                                .scheduleIdx(comment.getScheduleIdx())
                                .diaryIdx(comment.getDiaryIdx())
                                .dateTime(comment.getDateTime())
                                .content(comment.getContent())
                                .build())
                        .toList());
            }
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



    @Transactional
    @Override
    public void updateToAll(Long diaryIdx) {
        List<Shared> sharedList = sharedRepository.findByDiaryIdx(diaryIdx);

        if (sharedList.isEmpty()) {
            throw new IllegalStateException("공유 데이터가 없습니다.");
        }

        Shared mainShared = sharedList.get(0);
        mainShared.setFriendIdx(null);
        sharedRepository.save(mainShared);

        for (int i = 1; i < sharedList.size(); i++) {
            sharedRepository.delete(sharedList.get(i));
        }
    }


    @Transactional
    @Override
    public void deleteAllSharedByDiaryIdx(Long diaryIdx) {
        List<Shared> sharedList = sharedRepository.findByDiaryIdx(diaryIdx);

        if (!sharedList.isEmpty()) {
            for (Shared shared : sharedList) {
                sharedRepository.delete(shared);
            }
        }
    }
}
