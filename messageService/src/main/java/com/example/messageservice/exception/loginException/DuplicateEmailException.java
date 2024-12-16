package com.example.messageservice.exception.loginException;

import com.example.messageservice.exception.commonException.error.BizException;

public class DuplicateEmailException extends BizException {
    public DuplicateEmailException(LoginErrorCode loginErrorCode) {
        super(loginErrorCode);
    }
}
