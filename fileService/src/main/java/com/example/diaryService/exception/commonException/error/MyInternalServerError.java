package com.example.diaryService.exception.commonException.error;

import com.example.diaryService.exception.commonException.CommonErrorCode;

public class MyInternalServerError extends BizException {
    public MyInternalServerError(CommonErrorCode commonErrorCode) {
        super(commonErrorCode);
    }
}
