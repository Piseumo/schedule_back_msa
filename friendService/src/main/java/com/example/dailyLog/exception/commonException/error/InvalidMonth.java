package com.example.dailyLog.exception.commonException.error;

import com.example.dailyLog.exception.commonException.CommonErrorCode;

public class InvalidMonth extends BizException {
    public InvalidMonth(CommonErrorCode commonErrorCode) {
        super(commonErrorCode);
    }
}
