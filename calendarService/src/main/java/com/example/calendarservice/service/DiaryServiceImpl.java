package com.example.calendarservice.service;

import com.example.calendarservice.constant.Category;
import com.example.calendarservice.dto.request.DiaryRequestInsertDto;
import com.example.calendarservice.dto.request.DiaryRequestUpdateDto;
import com.example.calendarservice.dto.request.SharedRequestInsertDto;
import com.example.calendarservice.dto.request.SharedRequestUpdateDto;
import com.example.calendarservice.dto.response.DiaryResponseCategoryDto;
import com.example.calendarservice.dto.response.DiaryResponseDayDto;
import com.example.calendarservice.dto.response.DiaryResponseDayListDto;
import com.example.calendarservice.dto.response.DiaryResponseMonthDto;
import com.example.calendarservice.entity.Calendars;
import com.example.calendarservice.entity.Diary;
import com.example.calendarservice.entity.DiaryImage;
import com.example.calendarservice.entity.Shared;
import com.example.calendarservice.repository.CalendarRepository;
import com.example.calendarservice.repository.DiaryRepository;
import com.example.calendarservice.repository.DiaryImageRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.service.spi.ServiceException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.*;
import java.util.stream.Collectors;

import static com.example.calendarservice.constant.Share.ALL;
import static com.example.calendarservice.constant.Share.CHOOSE;

@Service
@RequiredArgsConstructor
public class DiaryServiceImpl implements DiaryService{

    private final DiaryRepository diaryRepository;
    private final CalendarRepository calendarRepository;
    private final DiaryImageRepository diaryImageRepository;
    private final ImageService imageService;
    private final FileService fileService;
    private final SharedService sharedService;


    // 월달력 전체 일기 조회(마커)
    @Transactional
    @Override
    public List<DiaryResponseMonthDto> findAllMonthDiary(Long calendarIdx, int year, int month){

        // 유효성 검사
        if (month < 1 || month > 12) {
            throw new IllegalArgumentException("Month must be between 1 and 12");
        }
        if (year < 1 || year > 9999) {
            throw new IllegalArgumentException("Year must be a positive number and within the range of valid years");
        }
//        if (!calendarRepository.existsById(calendarIdx)) {
//            throw new CalendarsNotFoundException(CalendarsErrorCode.CALENDARS_NOT_FOUND);
//        }

        try {
            LocalDate startOfMonth = LocalDate.of(year, month, 1);
            LocalDate endOfMonth = startOfMonth.withDayOfMonth(startOfMonth.lengthOfMonth());

            return diaryRepository.findByCalendarsIdxAndDateBetween(calendarIdx, startOfMonth, endOfMonth)
                    .stream().map(diary ->
                            DiaryResponseMonthDto.builder()
                                    .title(diary.getTitle())
                                    .date(diary.getDate())
                                    .build())
                    .sorted(Comparator.comparing(DiaryResponseMonthDto::getDate))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new ServiceException("Failed to find diary in DiaryService.findAllMonthDiary", e);
        }
    }


    // 개별 날짜 모든 다이어리 조회
    @Transactional
    @Override
    public List<DiaryResponseDayListDto> findDiaryByDayList(Long calendarIdx, int year, int month, int day){

        // 유효성 검사
//        if (!calendarRepository.existsById(calendarIdx)) {
//            throw new CalendarsNotFoundException(CalendarsErrorCode.CALENDARS_NOT_FOUND);
//        }
//        if (month < 1 || month > 12) {
//            throw new InvalidMonth(CommonErrorCode.INVALID_MONTH);
//        }
//        if (year < 1 || year > 9999) {
//            throw new InvalidYear(CommonErrorCode.INVALID_YEAR);
//        }
//        int lastDayOfMonth = YearMonth.of(year, month).lengthOfMonth();
//        if (day < 1 || day > lastDayOfMonth) {
//            throw new InvalidDay(CommonErrorCode.INVALID_DAY);
//        }

        try {
            LocalDate date = LocalDate.of(year, month, day);

            return diaryRepository.findByCalendarsIdxAndDate(calendarIdx, date)
                    .stream()
                    .map(diary -> {
                        return DiaryResponseDayListDto.builder()
                                .idx(diary.getIdx())
                                .title(diary.getTitle())
                                .content(diary.getContent())
                                .date(diary.getDate())
                                .category(diary.getCategory())
                                .images(diary.getDiaryImages().stream()
                                    .map(DiaryImage::getImgUrl)
                                    .collect(Collectors.toList()))
                                .share(diary.getShare())
                                .build();
                    })
                    .sorted(Comparator.comparing(DiaryResponseDayListDto::getDate))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new ServiceException("Failed to find diary in DiaryService.findDiaryByDayList", e);
        }
    }


    // 전체 및 카테고리별 전체 일기 조회
    @Transactional
    @Override
    public List<DiaryResponseCategoryDto> findDiaryCategory(Long calendarIdx, String category) {

//        if (!calendarRepository.existsById(calendarIdx)) {
//            throw new CalendarsNotFoundException(CalendarsErrorCode.CALENDARS_NOT_FOUND);
//        }

//        if (!"ALL".equalsIgnoreCase(category)) {
//            try {
//                Category.valueOf(category.toUpperCase());
//            } catch (IllegalArgumentException e) {
//                throw new InvalidCategory(DiaryErrorCode.INVALID_CATEGORY);
//            }
//        }

        try {
            if (category.equals("ALL")) {
                return diaryRepository.findByCalendarsIdx(calendarIdx)
                        .stream()
                        .map(diary -> DiaryResponseCategoryDto.builder()
                                .idx(diary.getIdx())
                                .title(diary.getTitle())
                                .date(diary.getDate())
                                .category(diary.getCategory())
                                .build())
                        .sorted(Comparator.comparing(DiaryResponseCategoryDto::getDate))
                        .collect(Collectors.toList());
            } else {
                Category categoryEnum = Category.valueOf(category.toUpperCase());
                return diaryRepository.findByCalendarsIdxAndCategory(calendarIdx, categoryEnum)
                        .stream()
                        .map(diary -> DiaryResponseCategoryDto.builder()
                                .idx(diary.getIdx())
                                .title(diary.getTitle())
                                .date(diary.getDate())
                                .category(diary.getCategory())
                                .build())
                        .sorted(Comparator.comparing(DiaryResponseCategoryDto::getDate))
                        .collect(Collectors.toList());
            }
        } catch (Exception e) {
            throw new ServiceException("Failed to find diary in DiaryService.findDiaryCategory", e);
        }
    }


    //개별 다이어리 조회
    @Transactional
    @Override
    public DiaryResponseDayDto findDiaryByDay(Long idx){

            Diary diary = diaryRepository.findById(idx)
                    .orElseThrow(() -> new IllegalArgumentException("일기가 존재하지 않습니다."));

        try {
            List<String> imageUrls = diary.getDiaryImages()
                    .stream()
                    .map(DiaryImage::getImgUrl)
                    .collect(Collectors.toList());

            return DiaryResponseDayDto.builder()
                    .diaryIdx(idx)
                    .title(diary.getTitle())
                    .content(diary.getContent())
                    .date(diary.getDate())
                    .category(diary.getCategory())
                    .share(diary.getShare())
                    .images(imageUrls) // 이미지 URL 리스트로 설정
                    .build();
        } catch (Exception e) {
            throw new ServiceException("Failed to find diary in DiaryService.findDiaryByDay", e);
        }
    }


    // 일기 입력
    @Transactional
    @Override
    public void saveDiary(DiaryRequestInsertDto diaryRequestInsertDto,List<MultipartFile> imageFileList) {
            Calendars calendarIdx = calendarRepository.findById(diaryRequestInsertDto.getCalendarIdx())
                    .orElseThrow(() -> new IllegalArgumentException("캘린더가 존재하지 않습니다."));

        try {

            Diary createDiary = Diary.builder()
                    .title(diaryRequestInsertDto.getTitle())
                    .content(diaryRequestInsertDto.getContent())
                    .date(diaryRequestInsertDto.getDate())
                    .category(diaryRequestInsertDto.getCategory())
                    .calendars(calendarIdx)
                    .share(diaryRequestInsertDto.getShare())
                    .build();
            createDiary = diaryRepository.save(createDiary);

            List<Long> friendIdxList = diaryRequestInsertDto.getFriendIdxList();
            if (friendIdxList != null && !friendIdxList.isEmpty()){
                for (Long friendIdx : friendIdxList){
                    SharedRequestInsertDto sharedRequestInsertDto = SharedRequestInsertDto.builder()
                            .diaryIdx(createDiary.getIdx())
                            .friendIdx(friendIdx)
                            .shareDateTime(LocalDateTime.now())
                            .build();
                    sharedService.saveShared(sharedRequestInsertDto);
                }
            }

            for (MultipartFile file : imageFileList) {
                if (!file.isEmpty()) {
                    DiaryImage diaryImage = imageService.saveDiaryImage(file,createDiary);
                    diaryImage.setDiary(createDiary);
                    diaryImageRepository.save(diaryImage);
                }
            }
        } catch (Exception e) {
            throw new ServiceException("Failed to save diary in DiaryService.saveDiary", e);
        }
    }


    // 일기 수정
    @Transactional
    @Override
    public void updateDiary(DiaryRequestUpdateDto diaryRequestUpdateDto, List<MultipartFile> imageFileList) {

        if (imageFileList == null) {
            imageFileList = Collections.emptyList();
        }
        Diary updateDiary = diaryRepository.findById(diaryRequestUpdateDto.getIdx())
                .orElseThrow(() -> new IllegalArgumentException("일기가 존재하지 않습니다."));

    //다이어리 기본 내용 수정
        try {
            if (diaryRequestUpdateDto.getTitle() != null) {
                updateDiary.setTitle(diaryRequestUpdateDto.getTitle());
            }
            if (diaryRequestUpdateDto.getContent() != null) {
                updateDiary.setContent(diaryRequestUpdateDto.getContent());
            }
//            if (diaryRequestUpdateDto.getDate() != null) {
//                updateDiary.setDate(diaryRequestUpdateDto.getDate());
//            }
            if (diaryRequestUpdateDto.getCategory() != null) {
                updateDiary.setCategory(diaryRequestUpdateDto.getCategory());
            }

            if (diaryRequestUpdateDto.getShare() != null){
                if (diaryRequestUpdateDto.getShare() == ALL){
                    SharedRequestInsertDto sharedRequestInsertDto = SharedRequestInsertDto.builder()
                            .diaryIdx(diaryRequestUpdateDto.getIdx())
                            .shareDateTime(LocalDateTime.now())
                            .build();
                    sharedService.updateToAll(sharedRequestInsertDto);
                } else if (diaryRequestUpdateDto.getShare() == CHOOSE) {
                    List<Long> friendIdxList = diaryRequestUpdateDto.getFriendIdxList();
                    if (friendIdxList != null && !friendIdxList.isEmpty()){
                        for (Long friendIdx : friendIdxList){
                            SharedRequestUpdateDto sharedRequestUpdateDto = SharedRequestUpdateDto.builder()
                                    .diaryIdx(diaryRequestUpdateDto.getIdx())
                                    .scheduleIdx(null)
                                    .friendIdx(friendIdx)
                                    .shareDateTime(LocalDateTime.now())
                                    .build();
                            sharedService.updateShared(sharedRequestUpdateDto);
                        }
                    }
                } else {
                    sharedService.deleteAllSharedByDiaryIdx(diaryRequestUpdateDto.getIdx());
                }
                    updateDiary.setShare(diaryRequestUpdateDto.getShare());
            }

            diaryRepository.save(updateDiary);

    // 이미지 삭제
            List<String> deleteImageList = diaryRequestUpdateDto.getDeletedImageList();
            if (deleteImageList != null && !deleteImageList.isEmpty()) {
                for (String imageUrl : deleteImageList) {
                    fileService.deleteFile(imageUrl);
                    diaryImageRepository.deleteByImgUrl(imageUrl);
                }
            }

    //다이어리 이미지 추가
            for (MultipartFile file : imageFileList) {
                if (!file.isEmpty()) {
                    DiaryImage diaryImage = imageService.saveDiaryImage(file,updateDiary);
                    diaryImage.setDiary(updateDiary);
                    diaryImageRepository.save(diaryImage);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new ServiceException("Failed to update diary in DiaryService.updateDiary", e);
        }
    }


    // 일기 삭제
    @Transactional
    @Override
    public void deleteDiary(Long idx){
        Diary diary = diaryRepository.findById(idx)
                .orElseThrow(() -> new IllegalArgumentException("일기가 존재하지 않습니다."));
        try {
            diaryRepository.delete(diary);
        }catch (Exception e) {
            throw new ServiceException("Failed to delete diary in DiaryService.deleteDiary", e);
        }
    }
}
