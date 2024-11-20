package com.example.diaryService.exception.loginException;

import com.example.diaryService.exception.commonException.error.BizException;

public class EmailNotFoundException extends BizException {
    public EmailNotFoundException(LoginErrorCode loginErrorCode) {
        super(loginErrorCode);
    }
}
