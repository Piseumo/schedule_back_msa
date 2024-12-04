package com.example.userservice.exception.loginException;

import com.example.userservice.exception.commonException.error.BizException;

public class DuplicateUserNameException extends BizException {
    public DuplicateUserNameException(LoginErrorCode loginErrorCode) {
        super(loginErrorCode);
    }
}
