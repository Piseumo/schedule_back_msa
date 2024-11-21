package com.example.userService.exception.commonException.error;

import com.example.userService.exception.commonException.CommonErrorCode;

public class InvalidDay extends BizException {
    public InvalidDay(CommonErrorCode commonErrorCode) {
        super(commonErrorCode);
    }
}
