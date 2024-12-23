package com.example.calendarservice.controller;

import com.example.calendarservice.dto.response.SearchResponseDto;
import com.example.calendarservice.service.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/home/search")
@RequiredArgsConstructor
@CrossOrigin
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
