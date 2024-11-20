package com.example.diaryService.exception.loginException;

import com.example.diaryService.exception.commonException.error.BizException;

public class UserPKException extends BizException {
    public UserPKException(LoginErrorCode loginErrorCode) {
        super(loginErrorCode);
    }
}
