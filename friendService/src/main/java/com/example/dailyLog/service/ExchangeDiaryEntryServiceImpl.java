package com.example.dailyLog.service;

import com.example.dailyLog.constant.ExchangeDiaryStatus;
import com.example.dailyLog.dto.request.ExchangeDiaryEntryRequestInsertDto;
import com.example.dailyLog.dto.request.ExchangeDiaryEntryRequestUpdateDto;
import com.example.dailyLog.dto.response.ExchangeDiaryEntryListResponseDto;
import com.example.dailyLog.dto.response.ExchangeDiaryResponseSummaryDto;
import com.example.dailyLog.entity.ExchangeDiary;
import com.example.dailyLog.entity.ExchangeDiaryEntry;
import com.example.dailyLog.entity.User;
import com.example.dailyLog.repository.ExchangeDiaryEntryRepository;
import com.example.dailyLog.repository.ExchangeDiaryRepository;
import com.example.dailyLog.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ExchangeDiaryEntryServiceImpl implements ExchangeDiaryEntryService {
    private final ExchangeDiaryEntryRepository entryRepository;
    private final ExchangeDiaryRepository exchangeDiaryRepository;
    private final UserRepository userRepository;

    // 일기 생성
    @Transactional
    @Override
    public ExchangeDiaryEntry createEntry(ExchangeDiaryEntryRequestInsertDto requestDto) {
        ExchangeDiary exchangeDiary = exchangeDiaryRepository.findById(requestDto.getDiaryId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid diary ID"));

        User creator = userRepository.findById(requestDto.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid user ID"));

        // 일기 엔트리 생성 및 저장
        ExchangeDiaryEntry entry = new ExchangeDiaryEntry();
        entry.setExchangeDiary(exchangeDiary);
        entry.setCreator(creator);
        entry.setTitle(requestDto.getTitle());
        entry.setContent(requestDto.getContent());
        entry.setCreatedAt(LocalDateTime.now());

        return entryRepository.save(entry);
    }

    // 일기 수정
    @Transactional
    @Override
    public ExchangeDiaryEntry updateEntry(ExchangeDiaryEntryRequestUpdateDto updateDto) {
        ExchangeDiaryEntry entry = entryRepository.findById(updateDto.getEntryId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid entry ID"));

        if (!entry.getCreator().getIdx().equals(updateDto.getUserId())) {
            throw new IllegalStateException("Only the creator can update the entry");
        }

        entry.setTitle(updateDto.getTitle());
        entry.setContent(updateDto.getContent());
        return entryRepository.save(entry);
    }

    // 일기 삭제
    @Transactional
    @Override
    public void deleteEntry(Long entryId) {
        ExchangeDiaryEntry entry = entryRepository.findById(entryId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid entry ID"));

        entryRepository.delete(entry);
    }

    // 교환 일기 미리보기 조회(제목,참석자)
    @Transactional
    @Override
    public List<ExchangeDiaryResponseSummaryDto> getUserExchangeDiaries(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user ID"));

        // 사용자가 참여한 교환일기만 가져오기
        List<ExchangeDiary> diaries = exchangeDiaryRepository.findByUser1OrUser2(user, user);

        Set<Long> uniqueParticipants = new HashSet<>();

        return diaries.stream()
                .filter(diary -> diary.getStatus() == ExchangeDiaryStatus.ACCEPTED) // 수락된 교환일기만 필터링
                .filter(diary -> {
                    // 다른 사용자가 참여한 고유 교환일기만 필터링
                    Long otherUserId = diary.getUser1().getIdx().equals(userId) ? diary.getUser2().getIdx() : diary.getUser1().getIdx();
                    return uniqueParticipants.add(otherUserId);
                })
                .map(diary -> {
                    List<String> participants = new ArrayList<>();
                    if (diary.getUser1().getIdx().equals(userId)) {
                        participants.add(diary.getUser1().getUserName());
                        participants.add(diary.getUser2().getUserName());
                    } else {
                        participants.add(diary.getUser2().getUserName());
                        participants.add(diary.getUser1().getUserName());
                    }

                    // 그룹명 가져오기
                    String groupName = diary.getGroupName();
                    return new ExchangeDiaryResponseSummaryDto(diary.getIdx(), groupName, participants);
                })
                .collect(Collectors.toList());
    }

    // 교환 일기 전체 내용 리스트 형식 반환
    @Transactional
    @Override
    public List<ExchangeDiaryEntryListResponseDto> getEntriesByDiaryId(Long userId, Long diaryId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user ID"));

        ExchangeDiary exchangeDiary = exchangeDiaryRepository.findById(diaryId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid diary ID"));

        // 권한 검사: 해당 교환일기에 현재 사용자가 포함되어 있는지 확인
        if (!exchangeDiary.getUser1().equals(user) && !exchangeDiary.getUser2().equals(user)) {
            throw new SecurityException("You do not have access to this diary.");
        }

        return entryRepository.findByExchangeDiary(exchangeDiary)
                .stream()
                .map(entry -> new ExchangeDiaryEntryListResponseDto(
                        entry.getIdx(),
                        entry.getTitle(),
                        entry.getContent(),
                        entry.getCreatedAt(),
                        entry.getCreator().getUserName() // 작성자 이름 추가
                ))
                .collect(Collectors.toList());
    }

}
