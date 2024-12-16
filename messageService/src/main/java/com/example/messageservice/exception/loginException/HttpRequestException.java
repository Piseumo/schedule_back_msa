package com.example.messageservice.exception.loginException;

import com.example.messageservice.exception.commonException.error.BizException;

public class HttpRequestException extends BizException {
    public HttpRequestException(LoginErrorCode loginErrorCode) {
        super(loginErrorCode);
    }
}
