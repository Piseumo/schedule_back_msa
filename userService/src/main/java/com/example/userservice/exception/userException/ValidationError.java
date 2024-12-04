package com.example.userservice.exception.userException;

import com.example.userservice.exception.commonException.error.BizException;

public class ValidationError extends BizException {
    public ValidationError(UserErrorCode userErrorCode) {
        super(userErrorCode);
    }
}
