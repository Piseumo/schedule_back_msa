package com.example.dailyLog.exception.loginException;

import com.example.dailyLog.exception.commonException.error.BizException;

public class DuplicateUserNameException extends BizException {
    public DuplicateUserNameException(LoginErrorCode loginErrorCode) {
        super(loginErrorCode);
    }
}
