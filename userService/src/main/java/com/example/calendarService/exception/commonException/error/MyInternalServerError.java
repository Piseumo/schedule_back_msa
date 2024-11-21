package com.example.calendarService.exception.commonException.error;

import com.example.calendarService.exception.commonException.CommonErrorCode;

public class MyInternalServerError extends BizException {
    public MyInternalServerError(CommonErrorCode commonErrorCode) {
        super(commonErrorCode);
    }
}
