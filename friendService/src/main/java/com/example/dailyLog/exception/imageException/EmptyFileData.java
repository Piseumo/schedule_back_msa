package com.example.dailyLog.exception.imageException;

import com.example.dailyLog.exception.commonException.error.BizException;

public class EmptyFileData extends BizException {
    public EmptyFileData(ImageErrorCode imageErrorCode) {
        super(imageErrorCode);
    }
}
