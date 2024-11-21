package com.example.calendarService.exception.imageException;

import com.example.calendarService.exception.commonException.error.BizException;

public class FailedDirectoryCreation extends BizException {
    public FailedDirectoryCreation(ImageErrorCode imageErrorCode) {
        super(imageErrorCode);
    }
}
