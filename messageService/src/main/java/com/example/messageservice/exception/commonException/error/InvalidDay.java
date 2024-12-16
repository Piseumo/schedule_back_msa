package com.example.messageservice.exception.commonException.error;

import com.example.messageservice.exception.commonException.CommonErrorCode;

public class InvalidDay extends BizException {
    public InvalidDay(CommonErrorCode commonErrorCode) {
        super(commonErrorCode);
    }
}
