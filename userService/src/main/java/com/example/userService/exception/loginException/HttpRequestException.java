package com.example.userService.exception.loginException;

import com.example.userService.exception.commonException.error.BizException;

public class HttpRequestException extends BizException {
    public HttpRequestException(LoginErrorCode loginErrorCode) {
        super(loginErrorCode);
    }
}
