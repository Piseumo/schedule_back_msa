package com.example.calendarservice.exception.diaryException;

import com.example.calendarservice.exception.commonException.error.BizException;

public class DiaryNotFoundException extends BizException {
    public DiaryNotFoundException(DiaryErrorCode diaryErrorCode) {
        super(diaryErrorCode);
    }
}
