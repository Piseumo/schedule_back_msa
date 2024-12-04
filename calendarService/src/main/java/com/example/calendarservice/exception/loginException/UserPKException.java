package com.example.calendarservice.exception.loginException;

import com.example.calendarservice.exception.commonException.error.BizException;

public class UserPKException extends BizException {
    public UserPKException(LoginErrorCode loginErrorCode) {
        super(loginErrorCode);
    }
}
