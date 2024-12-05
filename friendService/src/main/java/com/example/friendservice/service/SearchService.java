package com.example.friendservice.service;

import com.example.friendservice.dto.response.SearchResponseDto;

import java.util.List;

public interface SearchService {

    public List<SearchResponseDto> search(String query, String filterType);
}
