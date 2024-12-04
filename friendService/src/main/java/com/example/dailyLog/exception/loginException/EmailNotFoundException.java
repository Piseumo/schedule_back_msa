package com.example.dailyLog.exception.loginException;

import com.example.dailyLog.exception.commonException.error.BizException;

public class EmailNotFoundException extends BizException {
    public EmailNotFoundException(LoginErrorCode loginErrorCode) {
        super(loginErrorCode);
    }
}
