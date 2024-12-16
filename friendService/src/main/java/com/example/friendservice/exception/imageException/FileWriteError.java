package com.example.friendservice.exception.imageException;

import com.example.friendservice.exception.commonException.error.BizException;

public class FileWriteError extends BizException {
    public FileWriteError(ImageErrorCode imageErrorCode) {
        super(imageErrorCode);
    }
}
