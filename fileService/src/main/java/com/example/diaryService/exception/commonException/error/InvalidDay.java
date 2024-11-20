package com.example.diaryService.exception.commonException.error;

import com.example.diaryService.exception.commonException.CommonErrorCode;

public class InvalidDay extends BizException {
    public InvalidDay(CommonErrorCode commonErrorCode) {
        super(commonErrorCode);
    }
}
