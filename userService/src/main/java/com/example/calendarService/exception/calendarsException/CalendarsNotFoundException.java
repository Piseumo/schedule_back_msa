package com.example.calendarService.exception.calendarsException;

import com.example.calendarService.exception.commonException.error.BizException;

public class CalendarsNotFoundException extends BizException {
    public CalendarsNotFoundException(CalendarsErrorCode calendarsErrorCode) {
        super(calendarsErrorCode);
    }
}
