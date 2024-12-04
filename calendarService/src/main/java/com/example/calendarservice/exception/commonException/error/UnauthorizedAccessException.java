package com.example.calendarservice.exception.commonException.error;

import com.example.calendarservice.exception.commonException.CommonErrorCode;

public class UnauthorizedAccessException extends BizException {
    public UnauthorizedAccessException() {
      super(CommonErrorCode.UNAUTHORIZED_ACCESS);
    }
}
