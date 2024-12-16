package com.example.friendservice.exception.commonException.error;

import com.example.friendservice.exception.commonException.CommonErrorCode;

public class UnauthorizedAccessException extends BizException {
    public UnauthorizedAccessException() {
      super(CommonErrorCode.UNAUTHORIZED_ACCESS);
    }
}
