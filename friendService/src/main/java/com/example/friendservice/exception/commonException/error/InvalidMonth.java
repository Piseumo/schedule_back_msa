package com.example.friendservice.exception.commonException.error;

import com.example.friendservice.exception.commonException.CommonErrorCode;

public class InvalidMonth extends BizException {
    public InvalidMonth(CommonErrorCode commonErrorCode) {
        super(commonErrorCode);
    }
}
