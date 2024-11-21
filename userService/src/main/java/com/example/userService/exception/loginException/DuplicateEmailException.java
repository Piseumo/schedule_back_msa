package com.example.userService.exception.loginException;

import com.example.userService.exception.commonException.error.BizException;

public class DuplicateEmailException extends BizException {
    public DuplicateEmailException(LoginErrorCode loginErrorCode) {
        super(loginErrorCode);
    }
}
