package com.example.userservice.exception.imageException;

import com.example.userservice.exception.commonException.error.BizException;

public class FileWriteError extends BizException {
    public FileWriteError(ImageErrorCode imageErrorCode) {
        super(imageErrorCode);
    }
}
