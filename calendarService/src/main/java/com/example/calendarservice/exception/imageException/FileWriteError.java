package com.example.calendarservice.exception.imageException;

import com.example.calendarservice.exception.commonException.error.BizException;

public class FileWriteError extends BizException {
    public FileWriteError(ImageErrorCode imageErrorCode) {
        super(imageErrorCode);
    }
}
