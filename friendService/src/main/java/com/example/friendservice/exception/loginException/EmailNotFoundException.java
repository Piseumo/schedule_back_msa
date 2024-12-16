package com.example.friendservice.exception.loginException;

import com.example.friendservice.exception.commonException.error.BizException;

public class EmailNotFoundException extends BizException {
    public EmailNotFoundException(LoginErrorCode loginErrorCode) {
        super(loginErrorCode);
    }
}
