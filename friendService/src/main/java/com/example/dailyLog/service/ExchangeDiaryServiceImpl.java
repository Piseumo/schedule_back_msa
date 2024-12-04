package com.example.dailyLog.service;

import com.example.dailyLog.constant.ExchangeDiaryStatus;
import com.example.dailyLog.dto.response.UserSearchResponseDto;
import com.example.dailyLog.entity.ExchangeDiary;
import com.example.dailyLog.entity.User;
import com.example.dailyLog.repository.ExchangeDiaryRepository;
import com.example.dailyLog.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ExchangeDiaryServiceImpl implements ExchangeDiaryService{

    private final ExchangeDiaryRepository exchangeDiaryRepository;
    private final UserRepository userRepository;

    // 교환 일기 요청 보내기
    @Transactional
    @Override
    public Long sendExchangeDiaryRequest(Long requesterId, Long receiverId, String groupName) {
        if (requesterId.equals(receiverId)) {
            throw new IllegalArgumentException("Cannot send an exchange diary request to oneself.");
        }
        User requester = userRepository.findById(requesterId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid requester ID"));
        User receiver = userRepository.findById(receiverId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid receiver ID"));

        // 중복 요청 방지 로직
        if (exchangeDiaryRepository.existsByUser1AndUser2(requester, receiver) ||
                exchangeDiaryRepository.existsByUser1AndUser2(receiver, requester)) {
            throw new IllegalStateException("Exchange diary request already exists between these users.");
        }

        // 새로운 교환 일기 요청 생성
        ExchangeDiary exchangeDiary = new ExchangeDiary();
        exchangeDiary.setUser1(requester);
        exchangeDiary.setUser2(receiver);
        exchangeDiary.setStatus(ExchangeDiaryStatus.PENDING);
        exchangeDiary.setGroupName(groupName); // 그룹명 설정
        exchangeDiary.setCreatedAt(LocalDateTime.now());

        exchangeDiaryRepository.save(exchangeDiary);
        return  exchangeDiary.getIdx();
    }

    // 교환 일기 요청 조회
    @Transactional
    @Override
    public List<UserSearchResponseDto> getExchangeDiaryRequests(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user ID"));

        List<ExchangeDiary> pendingRequests = exchangeDiaryRepository.findAllByUser2AndStatus(user, ExchangeDiaryStatus.PENDING);

        return pendingRequests.stream()
                .map(exchangeDiary -> {
                    User requester = exchangeDiary.getUser1();
                    return new UserSearchResponseDto(
                            requester.getIdx(),                       // userId
                            exchangeDiary.getIdx(),                   // diaryId
                            requester.getUserName(),
                            requester.getProfileImage() != null ? requester.getProfileImage().getImgUrl() : "/images/default.png",
                            requester.getEmail()
                    );
                })
                .collect(Collectors.toList());
    }

    // 요청 수락
    @Transactional
    @Override
    public void acceptExchangeDiaryRequest(Long requesterId, Long receiverId) {

        User requester = userRepository.findById(requesterId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid requester ID"));
        User receiver = userRepository.findById(receiverId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid receiver ID"));

        // 기존 요청을 수락
        ExchangeDiary exchangeDiaryRequest = exchangeDiaryRepository.findByUser1AndUser2(requester, receiver)
                .orElseThrow(() -> new IllegalArgumentException("Exchange diary request not found"));

        exchangeDiaryRequest.setStatus(ExchangeDiaryStatus.ACCEPTED);
        exchangeDiaryRequest.setCreatedAt(LocalDateTime.now());
        exchangeDiaryRepository.save(exchangeDiaryRequest);

        // 반대 방향으로 교환 일기 요청이 없는 경우 생성하여 대칭적 관계 유지
        if (!exchangeDiaryRepository.existsByUser1AndUser2(receiver, requester)) {
            ExchangeDiary reverseExchangeDiary = new ExchangeDiary();
            reverseExchangeDiary.setUser1(receiver);
            reverseExchangeDiary.setUser2(requester);
            reverseExchangeDiary.setStatus(ExchangeDiaryStatus.ACCEPTED);
            reverseExchangeDiary.setGroupName(exchangeDiaryRequest.getGroupName());
            exchangeDiaryRepository.save(reverseExchangeDiary);
        }
    }

    // 요청 거절
    @Transactional
    @Override
    public void rejectExchangeDiaryRequest(Long exchangeDiaryId) {
        ExchangeDiary exchangeDiary = exchangeDiaryRepository.findById(exchangeDiaryId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid exchange diary ID"));

        exchangeDiary.setStatus(ExchangeDiaryStatus.REJECTED);
        exchangeDiaryRepository.save(exchangeDiary);
    }

    // 교환 일기 삭제
    @Transactional
    @Override
    public void deleteExchangeDiary(Long exchangeDiaryId) {
        ExchangeDiary exchangeDiary = exchangeDiaryRepository.findById(exchangeDiaryId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid exchange diary ID"));

        exchangeDiaryRepository.delete(exchangeDiary);
    }
}
