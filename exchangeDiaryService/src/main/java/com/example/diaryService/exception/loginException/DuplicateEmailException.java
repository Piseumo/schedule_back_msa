package com.example.diaryService.exception.loginException;

import com.example.diaryService.exception.commonException.error.BizException;

public class DuplicateEmailException extends BizException {
    public DuplicateEmailException(LoginErrorCode loginErrorCode) {
        super(loginErrorCode);
    }
}
