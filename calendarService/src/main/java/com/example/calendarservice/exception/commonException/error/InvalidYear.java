package com.example.calendarservice.exception.commonException.error;

import com.example.calendarservice.exception.commonException.CommonErrorCode;

public class InvalidYear extends BizException {
    public InvalidYear(CommonErrorCode commonErrorCode) {
        super(commonErrorCode);
    }
}
