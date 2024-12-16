package com.example.messageservice.exception.commonException.error;

import com.example.messageservice.exception.commonException.CommonErrorCode;

public class MyInternalServerError extends BizException {
    public MyInternalServerError(CommonErrorCode commonErrorCode) {
        super(commonErrorCode);
    }
}
