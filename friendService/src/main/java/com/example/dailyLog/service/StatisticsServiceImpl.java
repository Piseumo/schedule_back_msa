package com.example.dailyLog.service;

import com.example.dailyLog.constant.Category;
import com.example.dailyLog.dto.response.StatisticsCategoryDto;
import com.example.dailyLog.dto.response.StatisticsMonthDto;
import com.example.dailyLog.dto.response.StatisticsYearDto;
import com.example.dailyLog.exception.commonException.CommonErrorCode;
import com.example.dailyLog.exception.commonException.error.InvalidMonth;
import com.example.dailyLog.exception.commonException.error.InvalidYear;
import com.example.dailyLog.exception.diaryException.DatabaseError;
import com.example.dailyLog.exception.diaryException.DiaryErrorCode;
import com.example.dailyLog.exception.userException.UserErrorCode;
import com.example.dailyLog.exception.userException.UserNotFoundException;
import com.example.dailyLog.repository.StatisticsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.YearMonth;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StatisticsServiceImpl implements StatisticsService{

    private final StatisticsRepository statisticsRepository;


    // 카테고리 전체 통계
    @Override
    public List<StatisticsCategoryDto> getCategoryStatisticsByUser(Long userIdx) {

        if (userIdx == null || userIdx <= 0) {
            throw new UserNotFoundException(UserErrorCode.USER_NOT_FOUND);
        }

        Long totalCount;
        try {
            totalCount = statisticsRepository.findTotalCountByUser(userIdx);
        } catch (Exception e) {
            throw new DatabaseError(DiaryErrorCode.DATABASE_ERROR);
        }

        if (totalCount == null || totalCount == 0) {
            return Arrays.stream(Category.values())
                    .map(category -> new StatisticsCategoryDto(category, 0L, 0.0))
                    .collect(Collectors.toList());
        }

        try {
            return statisticsRepository.findCategoryCountsAsObjectArray(userIdx).stream()
                    .map(row -> {
                        Category category = (Category) row[0];
                        Long count = (Long) row[1];
                        double percentage = (totalCount > 0) ? (count * 100.0) / totalCount : 0.0;
                        return new StatisticsCategoryDto(category, count, percentage);
                    })
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new DatabaseError(DiaryErrorCode.DATABASE_ERROR);
        }
    }



    // 월 일기(전체) 작성률
    // 월 일기(카테고리별) 작성률
    @Override
    public List<StatisticsMonthDto> getMonthStatistics(Long userIdx, int year, int month) {

        if (userIdx == null || userIdx <= 0) {
            throw new UserNotFoundException(UserErrorCode.USER_NOT_FOUND);
        }
        if (year < 1900) {
            throw new InvalidYear(CommonErrorCode.INVALID_YEAR);
        }
        if (month < 1 || month > 12) {
            throw new InvalidMonth(CommonErrorCode.INVALID_MONTH);
        }

        int lastDayOfMonth = YearMonth.of(year, month).lengthOfMonth();

        Map<Integer, Map<Category, Long>> dailyCategoryCounts = new HashMap<>();
        for (int day = 1; day <= lastDayOfMonth; day++) {
            Map<Category, Long> categoryMap = new HashMap<>();
            for (Category category : Category.values()) {
                categoryMap.put(category, 0L); // 각 카테고리별 기본값 0 설정
            }
            dailyCategoryCounts.put(day, categoryMap);
        }

        List<Object[]> results;
        try {
            results = statisticsRepository.findDailyCategoryCountsByUserAndMonth(userIdx, year, month);
        } catch (Exception e) {
            throw new DatabaseError(DiaryErrorCode.DATABASE_ERROR); // 데이터베이스 에러 예외 처리
        }

        if (results == null || results.isEmpty()) {
            return generateDefaultMonthlyStatistics(year, month, dailyCategoryCounts);
        }

        for (Object[] result : results) {
            if (result == null || result.length < 3 || result[0] == null || result[1] == null || result[2] == null) {
                continue; // 쿼리 결과가 비정상인 경우 무시
            }
            int day = (int) result[0];
            Category category = (Category) result[1];
            Long count = (Long) result[2];
            dailyCategoryCounts.get(day).put(category, count);
        }
        return generateMonthlyStatisticsList(year, month, dailyCategoryCounts);
    }


    // 기본값을 사용한 StatisticsMonthDto 리스트 생성 메서드
    private List<StatisticsMonthDto> generateDefaultMonthlyStatistics(int year, int month, Map<Integer, Map<Category, Long>> dailyCategoryCounts) {
        List<StatisticsMonthDto> statistics = new ArrayList<>();
        final long[] cumulativeDiaryCount = {0}; // 누적 일기 개수를 배열로 선언

        dailyCategoryCounts.forEach((day, categoryMap) -> {
            long dailyDiaryCount = categoryMap.values().stream().mapToLong(Long::longValue).sum();
            cumulativeDiaryCount[0] += dailyDiaryCount;

            statistics.add(createStatisticsMonthDto(
                    year,
                    month,
                    day,
                    categoryMap,
                    dailyDiaryCount,               // 일별 일기 개수
                    cumulativeDiaryCount[0]        // 누적 일기 개수
            ));
        });
        return statistics;
    }

    // 유효한 데이터로 StatisticsMonthDto 리스트 생성 메서드
    private List<StatisticsMonthDto> generateMonthlyStatisticsList(int year, int month, Map<Integer, Map<Category, Long>> dailyCategoryCounts) {
        List<StatisticsMonthDto> statistics = new ArrayList<>();
        long cumulativeDiaryCount = 0;

        for (int day = 1; day <= YearMonth.of(year, month).lengthOfMonth(); day++) {
            Map<Category, Long> categoryMap = dailyCategoryCounts.getOrDefault(day, Collections.emptyMap());
            long dailyDiaryCount = categoryMap.values().stream().mapToLong(Long::longValue).sum();
            cumulativeDiaryCount += dailyDiaryCount;

            // createStatisticsMonthDto 메서드를 사용하여 StatisticsMonthDto 객체를 생성
            statistics.add(createStatisticsMonthDto(
                    year,
                    month,
                    day,
                    categoryMap,
                    dailyDiaryCount,
                    cumulativeDiaryCount
            ));
        }
        return statistics;
    }


    // StatisticsMonthDto 객체 생성 메서드
    private StatisticsMonthDto createStatisticsMonthDto(int year, int month, int day, Map<Category, Long> categoryMap, long dailyDiaryCount, long cumulativeDiaryCount) {
        double totalPercentage = cumulativeDiaryCount > 0 ? (cumulativeDiaryCount / 100.0) * 100 : 0.0;
        Map<Category, Double> categoryPercentageMap = categoryMap.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> dailyDiaryCount > 0 ? (entry.getValue() / (double) dailyDiaryCount) * 100 : 0.0
                ));

        String date = String.format("%04d-%02d-%02d", year, month, day); // "YYYY-MM-DD" 형식으로 설정

        return new StatisticsMonthDto(
                date,                  // 날짜 설정
                dailyDiaryCount,       // 일별 일기 개수
                cumulativeDiaryCount,  // 누적 일기 개수
                totalPercentage,       // 백분율 기준으로 계산된 총 비율
                categoryPercentageMap  // 카테고리 비율
        );
    }

    // 카테고리별 비율을 계산하는 메소드
    private Map<Category, Double> categoryPercentageMap(Map<Category, Long> categoryMap, long dailyDiaryCount) {
        return categoryMap.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> dailyDiaryCount > 0 ? (entry.getValue() / (double) dailyDiaryCount) * 100 : 0.0
                ));
    }



    // 연 일기(전체) 작성률
    // 연 일기(카테고리별) 작성률
    @Override
    public List<StatisticsYearDto> getYearStatistics(Long userIdx, int year) {
        if (userIdx == null || userIdx <= 0) {
            throw new UserNotFoundException(UserErrorCode.USER_NOT_FOUND);
        }
        if (year < 1900) {
            throw new InvalidYear(CommonErrorCode.INVALID_YEAR);
        }

        List<Object[]> results = statisticsRepository.findMonthlyCategoryCountsByUserAndYear(userIdx, year);

        Map<Integer, Long> monthlyDiaryCounts = new HashMap<>(); // 월별 일기 작성 횟수
        Map<Integer, Map<Category, Long>> categoryCountsMap = new HashMap<>(); // 카테고리 카운트를 위한 맵
        Long totalDiaryCount = 0L; // 총 일기 작성 횟수
        Long cumulativeCount = 0L; // 누적 카운트 초기화

        // 데이터 처리
        for (Object[] result : results) {
            Integer month = (Integer) result[0];
            Category category = (Category) result[1];
            Long count = (Long) result[2];

            monthlyDiaryCounts.put(month, monthlyDiaryCounts.getOrDefault(month, 0L) + count);
            categoryCountsMap.computeIfAbsent(month, k -> new HashMap<>())
                    .put(category, count); // 카운트 저장
        }

        List<StatisticsYearDto> statisticsList = new ArrayList<>();

        // 각 월별 통계 생성
        for (int month = 1; month <= 12; month++) {
            Long monthCount = monthlyDiaryCounts.getOrDefault(month, 0L);
            cumulativeCount += monthCount; // 누적 카운트 계산

            // 카테고리별 비율 계산
            Map<Category, Double> categoryPercentageMap = new HashMap<>();
            if (monthCount > 0) {
                Map<Category, Long> categoryCounts = categoryCountsMap.getOrDefault(month, new HashMap<>());

                for (Map.Entry<Category, Long> entry : categoryCounts.entrySet()) {
                    // 비율 계산
                    categoryPercentageMap.put(entry.getKey(), (entry.getValue() * 100.0) / monthCount);
                }
            }

            // StatisticsYearDto 객체 생성
            StatisticsYearDto monthlyStats = new StatisticsYearDto(
                    year + "년 " + month + "월", // year
                    monthCount,                 // totalDiaryCount
                    categoryPercentageMap,      // 카테고리별 비율
                    cumulativeCount              // 누적 일기 작성 횟수
            );

            statisticsList.add(monthlyStats);
        }

        // 중복된 데이터를 필터링
        return statisticsList.stream()
                .filter(stat -> stat.getTotalDiaryCount() > 0 || !stat.getCategoryPercentageMap().isEmpty())
                .collect(Collectors.toList());
    }



}
