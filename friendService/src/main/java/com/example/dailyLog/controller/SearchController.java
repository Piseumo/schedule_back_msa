package com.example.dailyLog.controller;

import com.example.dailyLog.dto.response.SearchResponseDto;
import com.example.dailyLog.service.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/home/search")
@RequiredArgsConstructor
public class SearchController {

    private final SearchService searchService;

    @GetMapping
    public ResponseEntity<List<SearchResponseDto>> search(
            @RequestParam(name = "query") String query,
            @RequestParam(name = "filterType", defaultValue = "ALL") String filterType){
        List<SearchResponseDto> results = searchService.search(query, filterType);
        return ResponseEntity.ok(results);
    }
}
