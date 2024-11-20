package com.example.diaryService.service;

import com.example.diaryService.dto.SearchResponseDto;

import java.util.List;

public interface SearchService {

    public List<SearchResponseDto> search(String query, String filterType);
}
