package com.example.calendarservice.service;

import com.example.calendarservice.dto.request.CommentsRequestInsertDto;
import com.example.calendarservice.dto.request.CommentsRequestUpdateDto;
import com.example.calendarservice.dto.request.SharedRequestInsertDto;
import com.example.calendarservice.dto.request.SharedRequestUpdateDto;
import com.example.calendarservice.dto.response.CommentsResponseAllDto;
import com.example.calendarservice.dto.response.CommentsResponseInsertDto;
import com.example.calendarservice.dto.response.SharedContentDto;

import java.util.List;

public interface SharedService {

    public void saveShared(SharedRequestInsertDto sharedRequestInsertDto);
    public void updateShared(SharedRequestUpdateDto sharedRequestUpdateDto);
    public List<SharedContentDto> findAllShared(Long userIdx);
    public SharedContentDto findOneShared(Long sharedIdx);
    public void deleteShared(Long sharedIdx);
    public void deleteContentShared(SharedRequestUpdateDto sharedRequestUpdateDto);
    public List<SharedContentDto> findSharedByUser(Long userIdx);
    public CommentsResponseInsertDto saveComments(CommentsRequestInsertDto commentsRequestInsertDto);
    public List<CommentsResponseAllDto> findAllComments(String type, Long mixedIdx);
    public void updateComments(CommentsRequestUpdateDto commentsRequestUpdateDto);
    public void deleteComments(Long commentsIdx);

    void updateToAll(SharedRequestInsertDto sharedRequestInsertDto);
    void updateToAllSchedule(SharedRequestInsertDto sharedRequestInsertDto);
    void deleteAllSharedByDiaryIdx(Long diaryIdx);
    void deleteAllSharedByScheduleIdx(Long scheduleIdx);
}
