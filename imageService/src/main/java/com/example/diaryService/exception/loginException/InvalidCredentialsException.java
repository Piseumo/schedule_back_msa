package com.example.diaryService.exception.loginException;

import com.example.diaryService.exception.commonException.error.BizException;

public class InvalidCredentialsException extends BizException {
    public InvalidCredentialsException(LoginErrorCode loginErrorCode) {
        super(loginErrorCode);
    }
}
