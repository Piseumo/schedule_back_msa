package com.example.diaryService.exception.commonException.error;

import com.example.diaryService.exception.commonException.CommonErrorCode;

public class UnauthorizedAccessException extends BizException {
    public UnauthorizedAccessException() {
      super(CommonErrorCode.UNAUTHORIZED_ACCESS);
    }
}
