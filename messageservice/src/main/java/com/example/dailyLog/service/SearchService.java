package com.example.dailyLog.service;

import com.example.dailyLog.dto.response.SearchResponseDto;

import java.util.List;

public interface SearchService {

    public List<SearchResponseDto> search(String query, String filterType);
}
