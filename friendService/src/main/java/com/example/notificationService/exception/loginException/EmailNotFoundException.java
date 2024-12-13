package com.example.notificationService.exception.loginException;

import com.example.notificationService.exception.commonException.error.BizException;

public class EmailNotFoundException extends BizException {
    public EmailNotFoundException(LoginErrorCode loginErrorCode) {
        super(loginErrorCode);
    }
}
