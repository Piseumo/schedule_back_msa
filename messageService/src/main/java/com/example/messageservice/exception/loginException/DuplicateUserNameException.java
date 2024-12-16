package com.example.messageservice.exception.loginException;

import com.example.messageservice.exception.commonException.error.BizException;

public class DuplicateUserNameException extends BizException {
    public DuplicateUserNameException(LoginErrorCode loginErrorCode) {
        super(loginErrorCode);
    }
}
