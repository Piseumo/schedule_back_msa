package com.example.dailyLog.exception.loginException;

import com.example.dailyLog.exception.commonException.error.BizException;

public class DuplicateEmailException extends BizException {
    public DuplicateEmailException(LoginErrorCode loginErrorCode) {
        super(loginErrorCode);
    }
}
