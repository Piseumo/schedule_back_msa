package com.example.userservice.exception.calendarsException;

import com.example.userservice.exception.commonException.error.BizException;

public class CalendarsNotFoundException extends BizException {
    public CalendarsNotFoundException(CalendarsErrorCode calendarsErrorCode) {
        super(calendarsErrorCode);
    }
}
