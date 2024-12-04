package com.example.calendarservice.exception.imageException;

import com.example.calendarservice.exception.commonException.error.BizException;

public class InvalidUploadPath extends BizException {
    public InvalidUploadPath(ImageErrorCode imageErrorCode) {
        super(imageErrorCode);
    }
}
