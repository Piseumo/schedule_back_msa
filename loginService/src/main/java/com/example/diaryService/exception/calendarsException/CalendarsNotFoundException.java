package com.example.diaryService.exception.calendarsException;

import com.example.diaryService.exception.commonException.error.BizException;

public class CalendarsNotFoundException extends BizException {
    public CalendarsNotFoundException(CalendarsErrorCode calendarsErrorCode) {
        super(calendarsErrorCode);
    }
}
