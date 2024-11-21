package com.example.calendarService.exception.loginException;

import com.example.calendarService.exception.commonException.error.BizException;

public class DuplicateEmailException extends BizException {
    public DuplicateEmailException(LoginErrorCode loginErrorCode) {
        super(loginErrorCode);
    }
}
