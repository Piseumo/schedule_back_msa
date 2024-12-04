package com.example.userservice.service;

import com.example.userservice.dto.response.SearchResponseDto;

import java.util.List;

public interface SearchService {

    public List<SearchResponseDto> search(String query, String filterType);
}
