package com.example.friendservice.exception.userException;

import com.example.friendservice.exception.commonException.error.BizException;


public class UserNotFoundException extends BizException {
    public UserNotFoundException(UserErrorCode userErrorCode) {

      super(userErrorCode);
    }
}
