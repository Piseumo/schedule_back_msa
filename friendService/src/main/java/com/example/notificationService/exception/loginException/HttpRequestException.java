package com.example.notificationService.exception.loginException;

import com.example.notificationService.exception.commonException.error.BizException;

public class HttpRequestException extends BizException {
    public HttpRequestException(LoginErrorCode loginErrorCode) {
        super(loginErrorCode);
    }
}
