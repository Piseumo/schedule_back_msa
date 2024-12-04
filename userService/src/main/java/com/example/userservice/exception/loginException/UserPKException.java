package com.example.userservice.exception.loginException;

import com.example.userservice.exception.commonException.error.BizException;

public class UserPKException extends BizException {
    public UserPKException(LoginErrorCode loginErrorCode) {
        super(loginErrorCode);
    }
}
