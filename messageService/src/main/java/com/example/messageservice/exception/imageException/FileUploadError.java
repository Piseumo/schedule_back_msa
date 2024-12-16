package com.example.messageservice.exception.imageException;

import com.example.messageservice.exception.commonException.error.BizException;

public class FileUploadError extends BizException {
    public FileUploadError(ImageErrorCode imageErrorCode) {
        super(imageErrorCode);
    }
}
