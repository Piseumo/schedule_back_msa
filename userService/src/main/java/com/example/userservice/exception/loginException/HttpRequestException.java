package com.example.userservice.exception.loginException;

import com.example.userservice.exception.commonException.error.BizException;

public class HttpRequestException extends BizException {
    public HttpRequestException(LoginErrorCode loginErrorCode) {
        super(loginErrorCode);
    }
}
