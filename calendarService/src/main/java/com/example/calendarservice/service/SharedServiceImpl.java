package com.example.calendarservice.service;

import com.example.calendarservice.dto.response.SharedContentDto;
import com.example.calendarservice.dto.response.UserSearchResponseDto;
import com.example.calendarservice.entity.Shared;
import com.example.calendarservice.feign.FriendClient;
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

@Service
@RequiredArgsConstructor
public class SharedServiceImpl implements SharedService{

    private final SharedRepository sharedRepository;
    private final FriendClient friendClient;
    private final ScheduleRepository scheduleRepository;
    private final DiaryRepository diaryRepository;


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
                .sorted(Comparator.comparing(SharedContentDto::getShareDate))
                .collect(Collectors.toList());

        return result;
    }


    // 클릭 했을 때 쓰레드 컨텐츠 개별 화면 조회 (+댓글 조회 메소드 호출)



    // 클릭 했을 때 쓰레드 컨텐츠 개별 화면에서 공유 취소



    // 사용자 개인의 공유 컨텐츠 모아보기


    // 해당 게시글 전체 댓글 조회


    // 댓글 수정


    // 댓글 삭제

}
