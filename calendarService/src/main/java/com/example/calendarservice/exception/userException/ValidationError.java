package com.example.calendarservice.exception.userException;

import com.example.calendarservice.exception.commonException.error.BizException;
import com.example.calendarservice.exception.userException.UserErrorCode;

public class ValidationError extends BizException {
    public ValidationError(UserErrorCode userErrorCode) {
        super(userErrorCode);
    }
}
