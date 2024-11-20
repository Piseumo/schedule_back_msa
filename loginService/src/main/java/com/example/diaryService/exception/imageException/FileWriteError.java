package com.example.diaryService.exception.imageException;

import com.example.diaryService.exception.commonException.error.BizException;

public class FileWriteError extends BizException {
    public FileWriteError(ImageErrorCode imageErrorCode) {
        super(imageErrorCode);
    }
}
