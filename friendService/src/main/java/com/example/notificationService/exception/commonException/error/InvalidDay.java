package com.example.notificationService.exception.commonException.error;

import com.example.notificationService.exception.commonException.CommonErrorCode;

public class InvalidDay extends BizException {
    public InvalidDay(CommonErrorCode commonErrorCode) {
        super(commonErrorCode);
    }
}
