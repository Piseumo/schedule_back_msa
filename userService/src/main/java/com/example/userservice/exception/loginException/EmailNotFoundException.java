package com.example.userservice.exception.loginException;

import com.example.userservice.exception.commonException.error.BizException;

public class EmailNotFoundException extends BizException {
    public EmailNotFoundException(LoginErrorCode loginErrorCode) {
        super(loginErrorCode);
    }
}
