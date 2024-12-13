package com.example.dailyLog.exception.imageException;

import com.example.dailyLog.exception.commonException.error.BizException;

public class InvalidUploadPath extends BizException {
    public InvalidUploadPath(ImageErrorCode imageErrorCode) {
        super(imageErrorCode);
    }
}
