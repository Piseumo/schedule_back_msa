package com.example.userservice.exception.imageException;

import com.example.userservice.exception.commonException.error.BizException;

public class InvalidFileName extends BizException {
    public InvalidFileName(ImageErrorCode imageErrorCode) {
        super(imageErrorCode);
    }
}
