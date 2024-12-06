package com.example.friendservice.exception.commonException.error;

import com.example.friendservice.exception.commonException.CommonErrorCode;

public class InvalidYear extends BizException {
    public InvalidYear(CommonErrorCode commonErrorCode) {
        super(commonErrorCode);
    }
}
