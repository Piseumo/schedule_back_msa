package com.example.messageservice.exception.commonException.error;

import com.example.messageservice.exception.commonException.CommonErrorCode;

public class InvalidMonth extends BizException {
    public InvalidMonth(CommonErrorCode commonErrorCode) {
        super(commonErrorCode);
    }
}
