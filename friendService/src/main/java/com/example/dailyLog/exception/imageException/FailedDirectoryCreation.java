package com.example.dailyLog.exception.imageException;

import com.example.dailyLog.exception.commonException.error.BizException;

public class FailedDirectoryCreation extends BizException {
    public FailedDirectoryCreation(ImageErrorCode imageErrorCode) {
        super(imageErrorCode);
    }
}
