package com.example.friendservice.exception.imageException;

import com.example.friendservice.exception.commonException.error.BizException;

public class FileUploadError extends BizException {
    public FileUploadError(ImageErrorCode imageErrorCode) {
        super(imageErrorCode);
    }
}
