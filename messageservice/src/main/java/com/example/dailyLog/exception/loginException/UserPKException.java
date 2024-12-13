package com.example.dailyLog.exception.loginException;

import com.example.dailyLog.exception.commonException.error.BizException;

public class UserPKException extends BizException {
    public UserPKException(LoginErrorCode loginErrorCode) {
        super(loginErrorCode);
    }
}
