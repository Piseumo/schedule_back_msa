package com.example.notificationService.exception.loginException;

import com.example.notificationService.exception.commonException.error.BizException;

public class InvalidCredentialsException extends BizException {
    public InvalidCredentialsException(LoginErrorCode loginErrorCode) {
        super(loginErrorCode);
    }
}
