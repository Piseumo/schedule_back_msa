package com.example.calendarservice.exception.imageException;

import com.example.calendarservice.exception.commonException.error.BizException;

public class EmptyFileData extends BizException {
    public EmptyFileData(ImageErrorCode imageErrorCode) {
        super(imageErrorCode);
    }
}
