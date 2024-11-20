package com.example.diaryService.exception.commonException.error;

import com.example.diaryService.exception.commonException.CommonErrorCode;

public class InvalidYear extends BizException {
    public InvalidYear(CommonErrorCode commonErrorCode) {
        super(commonErrorCode);
    }
}
