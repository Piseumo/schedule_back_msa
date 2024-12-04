package com.example.userservice.exception.userException;

import com.example.userservice.exception.commonException.error.BizException;


public class UserNotFoundException extends BizException {
    public UserNotFoundException(UserErrorCode userErrorCode) {

      super(userErrorCode);
    }
}
