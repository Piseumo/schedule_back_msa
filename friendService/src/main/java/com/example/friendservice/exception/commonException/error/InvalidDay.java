package com.example.friendservice.exception.commonException.error;

import com.example.friendservice.exception.commonException.CommonErrorCode;

public class InvalidDay extends BizException {
    public InvalidDay(CommonErrorCode commonErrorCode) {
        super(commonErrorCode);
    }
}
