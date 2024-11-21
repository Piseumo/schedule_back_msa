package com.example.calendarService.exception.loginException;

import com.example.calendarService.exception.commonException.error.BizException;

public class UserPKException extends BizException {
    public UserPKException(LoginErrorCode loginErrorCode) {
        super(loginErrorCode);
    }
}
