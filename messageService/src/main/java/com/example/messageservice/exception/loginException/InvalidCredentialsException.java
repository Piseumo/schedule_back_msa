package com.example.messageservice.exception.loginException;

import com.example.messageservice.exception.commonException.error.BizException;

public class InvalidCredentialsException extends BizException {
    public InvalidCredentialsException(LoginErrorCode loginErrorCode) {
        super(loginErrorCode);
    }
}
