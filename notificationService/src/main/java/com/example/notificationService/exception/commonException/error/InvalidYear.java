package com.example.notificationService.exception.commonException.error;

import com.example.notificationService.exception.commonException.CommonErrorCode;

public class InvalidYear extends BizException {
    public InvalidYear(CommonErrorCode commonErrorCode) {
        super(commonErrorCode);
    }
}
