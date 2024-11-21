package com.example.calendarService.exception.diaryException;

import com.example.calendarService.exception.commonException.error.BizException;

public class DiaryNotFoundException extends BizException {
    public DiaryNotFoundException(DiaryErrorCode diaryErrorCode) {
        super(diaryErrorCode);
    }
}
