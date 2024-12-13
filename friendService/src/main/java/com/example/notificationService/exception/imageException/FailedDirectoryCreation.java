package com.example.notificationService.exception.imageException;

import com.example.notificationService.exception.commonException.error.BizException;

public class FailedDirectoryCreation extends BizException {
    public FailedDirectoryCreation(ImageErrorCode imageErrorCode) {
        super(imageErrorCode);
    }
}
