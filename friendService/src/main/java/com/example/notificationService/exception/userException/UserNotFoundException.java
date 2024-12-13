package com.example.notificationService.exception.userException;

import com.example.notificationService.exception.commonException.error.BizException;


public class UserNotFoundException extends BizException {
    public UserNotFoundException(UserErrorCode userErrorCode) {

      super(userErrorCode);
    }
}
