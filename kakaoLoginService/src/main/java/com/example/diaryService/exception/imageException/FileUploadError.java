package com.example.diaryService.exception.imageException;

import com.example.diaryService.exception.commonException.error.BizException;

public class FileUploadError extends BizException {
    public FileUploadError(ImageErrorCode imageErrorCode) {
        super(imageErrorCode);
    }
}
