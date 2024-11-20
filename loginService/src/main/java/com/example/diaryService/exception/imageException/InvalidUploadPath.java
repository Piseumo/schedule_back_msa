package com.example.diaryService.exception.imageException;

import com.example.diaryService.exception.commonException.error.BizException;

public class InvalidUploadPath extends BizException {
    public InvalidUploadPath(ImageErrorCode imageErrorCode) {
        super(imageErrorCode);
    }
}
