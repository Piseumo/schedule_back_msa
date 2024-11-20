package com.example.diaryService.exception.userException;

import com.example.diaryService.exception.commonException.error.BizException;

public class ValidationError extends BizException {
    public ValidationError(UserErrorCode userErrorCode) {
        super(userErrorCode);
    }
}
