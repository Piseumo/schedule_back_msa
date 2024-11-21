package com.example.calendarService.exception.loginException;

import com.example.calendarService.exception.commonException.error.BizException;

public class InvalidCredentialsException extends BizException {
    public InvalidCredentialsException(LoginErrorCode loginErrorCode) {
        super(loginErrorCode);
    }
}
