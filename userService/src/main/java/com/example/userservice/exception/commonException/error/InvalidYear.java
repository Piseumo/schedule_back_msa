package com.example.userservice.exception.commonException.error;

import com.example.userservice.exception.commonException.CommonErrorCode;

public class InvalidYear extends BizException {
    public InvalidYear(CommonErrorCode commonErrorCode) {
        super(commonErrorCode);
    }
}
