package com.example.messageservice.exception.calendarsException;

import com.example.messageservice.exception.commonException.error.BizException;

public class CalendarsNotFoundException extends BizException {
    public CalendarsNotFoundException(CalendarsErrorCode calendarsErrorCode) {
        super(calendarsErrorCode);
    }
}
