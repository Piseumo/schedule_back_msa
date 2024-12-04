package com.example.calendarservice.exception.userException;

import com.example.calendarservice.exception.commonException.error.BizException;

public class ValidationError extends BizException {
    public ValidationError(UserErrorCode userErrorCode) {
        super(userErrorCode);
    }
}
