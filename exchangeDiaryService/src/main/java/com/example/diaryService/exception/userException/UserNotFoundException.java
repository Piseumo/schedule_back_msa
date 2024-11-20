package com.example.diaryService.exception.userException;

import com.example.diaryService.exception.commonException.error.BizException;


public class UserNotFoundException extends BizException {
    public UserNotFoundException(UserErrorCode userErrorCode) {

      super(userErrorCode);
    }
}
