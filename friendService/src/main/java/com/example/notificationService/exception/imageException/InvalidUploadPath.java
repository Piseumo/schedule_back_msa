package com.example.notificationService.exception.imageException;

import com.example.notificationService.exception.commonException.error.BizException;

public class InvalidUploadPath extends BizException {
    public InvalidUploadPath(ImageErrorCode imageErrorCode) {
        super(imageErrorCode);
    }
}
