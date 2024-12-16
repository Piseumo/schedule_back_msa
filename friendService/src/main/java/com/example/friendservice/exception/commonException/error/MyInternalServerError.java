package com.example.friendservice.exception.commonException.error;

import com.example.friendservice.exception.commonException.CommonErrorCode;

public class MyInternalServerError extends BizException {
    public MyInternalServerError(CommonErrorCode commonErrorCode) {
        super(commonErrorCode);
    }
}
