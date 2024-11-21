package com.example.userService.exception.commonException.error;

import com.example.userService.exception.commonException.CommonErrorCode;

public class MyInternalServerError extends BizException {
    public MyInternalServerError(CommonErrorCode commonErrorCode) {
        super(commonErrorCode);
    }
}
