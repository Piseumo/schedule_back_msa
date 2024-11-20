package com.example.diaryService.exception.loginException;

import com.example.diaryService.exception.commonException.error.BizException;

public class HttpRequestException extends BizException {
    public HttpRequestException(LoginErrorCode loginErrorCode) {
        super(loginErrorCode);
    }
}
