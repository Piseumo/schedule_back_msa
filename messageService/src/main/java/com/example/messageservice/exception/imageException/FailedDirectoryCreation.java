package com.example.messageservice.exception.imageException;

import com.example.messageservice.exception.commonException.error.BizException;

public class FailedDirectoryCreation extends BizException {
    public FailedDirectoryCreation(ImageErrorCode imageErrorCode) {
        super(imageErrorCode);
    }
}
