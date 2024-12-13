package com.example.dailyLog.service;

import com.example.dailyLog.dto.response.SearchResponseDto;
import com.example.dailyLog.repository.SearchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SearchServiceImpl implements SearchService{

    private final SearchRepository searchRepository;

    @Override
    public List<SearchResponseDto> search(String query, String filterType) {
        List<Object[]> results;

        if ("SCHEDULE".equalsIgnoreCase(filterType)) {
            results = searchRepository.searchSchedule(query);
        } else if ("DIARY".equalsIgnoreCase(filterType)) {
            results = searchRepository.searchDiary(query);
        } else {
            results = searchRepository.searchAll(query);
        }

        return results.stream()
                .map(result -> new SearchResponseDto(
                        (Long) result[0],
                        (String) result[1],
                        (String) result[2],
                        result[3] != null ? ((Timestamp) result[3]).toLocalDateTime() : null,
                        result[4] != null ? ((Date) result[4]).toLocalDate() : null,
                        (String) result[5],
                        (String) result[6]
                ))
                .collect(Collectors.toList());
    }
}
