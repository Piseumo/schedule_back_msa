package com.example.notificationService.exception.imageException;

import com.example.notificationService.exception.commonException.error.BizException;

public class FileWriteError extends BizException {
    public FileWriteError(ImageErrorCode imageErrorCode) {
        super(imageErrorCode);
    }
}
