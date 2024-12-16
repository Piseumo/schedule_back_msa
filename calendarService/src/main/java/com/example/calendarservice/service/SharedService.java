package com.example.calendarservice.service;

import com.example.calendarservice.dto.response.SharedContentDto;

import java.util.List;

public interface SharedService {

    public List<SharedContentDto> findAllShared(Long userIdx);
}
