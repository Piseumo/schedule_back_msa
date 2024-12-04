package com.example.dailyLog.exception.calendarsException;

import com.example.dailyLog.exception.commonException.error.BizException;

public class CalendarsNotFoundException extends BizException {
    public CalendarsNotFoundException(CalendarsErrorCode calendarsErrorCode) {
        super(calendarsErrorCode);
    }
}
