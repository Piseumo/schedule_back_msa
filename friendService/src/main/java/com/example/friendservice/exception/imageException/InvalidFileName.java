package com.example.friendservice.exception.imageException;

import com.example.friendservice.exception.commonException.error.BizException;

public class InvalidFileName extends BizException {
    public InvalidFileName(ImageErrorCode imageErrorCode) {
        super(imageErrorCode);
    }
}
