package com.example.calendarservice.exception.imageException;

import com.example.calendarservice.exception.commonException.error.BizException;

public class FileUploadError extends BizException {
    public FileUploadError(ImageErrorCode imageErrorCode) {
        super(imageErrorCode);
    }
}
