package com.example.messageservice.exception.userException;

import com.example.messageservice.exception.commonException.error.BizException;


public class UserNotFoundException extends BizException {
    public UserNotFoundException(UserErrorCode userErrorCode) {

      super(userErrorCode);
    }
}
