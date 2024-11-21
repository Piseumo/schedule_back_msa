package com.example.userService.exception.commonException.error;

import com.example.userService.exception.commonException.CommonErrorCode;

public class UnauthorizedAccessException extends BizException {
    public UnauthorizedAccessException() {
      super(CommonErrorCode.UNAUTHORIZED_ACCESS);
    }
}
