package com.example.notificationService.exception.loginException;

import com.example.notificationService.exception.commonException.error.BizException;

public class DuplicateUserNameException extends BizException {
    public DuplicateUserNameException(LoginErrorCode loginErrorCode) {
        super(loginErrorCode);
    }
}
