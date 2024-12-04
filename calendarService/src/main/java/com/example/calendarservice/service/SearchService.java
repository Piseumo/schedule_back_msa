package com.example.calendarservice.service;

import com.example.calendarservice.dto.response.SearchResponseDto;

import java.util.List;

public interface SearchService {

    public List<SearchResponseDto> search(String query, String filterType);
}
