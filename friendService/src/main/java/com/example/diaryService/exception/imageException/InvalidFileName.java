package com.example.diaryService.exception.imageException;

import com.example.diaryService.exception.commonException.error.BizException;

public class InvalidFileName extends BizException {
    public InvalidFileName(ImageErrorCode imageErrorCode) {
        super(imageErrorCode);
    }
}
