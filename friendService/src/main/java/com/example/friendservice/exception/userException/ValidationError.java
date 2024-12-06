package com.example.friendservice.exception.userException;

import com.example.friendservice.exception.commonException.error.BizException;

public class ValidationError extends BizException {
    public ValidationError(UserErrorCode userErrorCode) {
        super(userErrorCode);
    }
}
