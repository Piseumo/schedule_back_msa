package com.example.calendarservice.exception.loginException;

import com.example.calendarservice.exception.commonException.error.BizException;

public class InvalidCredentialsException extends BizException {
    public InvalidCredentialsException(LoginErrorCode loginErrorCode) {
        super(loginErrorCode);
    }
}
