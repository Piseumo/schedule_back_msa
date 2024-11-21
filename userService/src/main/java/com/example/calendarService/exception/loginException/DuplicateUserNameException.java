package com.example.calendarService.exception.loginException;

import com.example.calendarService.exception.commonException.error.BizException;

public class DuplicateUserNameException extends BizException {
    public DuplicateUserNameException(LoginErrorCode loginErrorCode) {
        super(loginErrorCode);
    }
}
