package com.example.calendarservice.exception.commonException.error;

import com.example.calendarservice.exception.commonException.CommonErrorCode;

public class InvalidMonth extends BizException {
    public InvalidMonth(CommonErrorCode commonErrorCode) {
        super(commonErrorCode);
    }
}
