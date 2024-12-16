package com.example.messageservice.exception.commonException.error;

import com.example.messageservice.exception.commonException.CommonErrorCode;

public class UnauthorizedAccessException extends BizException {
    public UnauthorizedAccessException() {
      super(CommonErrorCode.UNAUTHORIZED_ACCESS);
    }
}
