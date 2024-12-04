package com.example.dailyLog.exception.imageException;

import com.example.dailyLog.exception.commonException.error.BizException;

public class InvalidFileName extends BizException {
    public InvalidFileName(ImageErrorCode imageErrorCode) {
        super(imageErrorCode);
    }
}
