package com.example.dailyLog.exception.imageException;

import com.example.dailyLog.exception.commonException.error.BizException;

public class FileUploadError extends BizException {
    public FileUploadError(ImageErrorCode imageErrorCode) {
        super(imageErrorCode);
    }
}
