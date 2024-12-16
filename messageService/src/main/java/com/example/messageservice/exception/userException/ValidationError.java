package com.example.messageservice.exception.userException;

import com.example.messageservice.exception.commonException.error.BizException;

public class ValidationError extends BizException {
    public ValidationError(UserErrorCode userErrorCode) {
        super(userErrorCode);
    }
}
