package com.example.diaryService.exception.commonException.error;

import com.example.diaryService.exception.commonException.CommonErrorCode;

public class InvalidMonth extends BizException {
    public InvalidMonth(CommonErrorCode commonErrorCode) {
        super(commonErrorCode);
    }
}
