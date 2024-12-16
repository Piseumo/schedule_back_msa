package com.example.messageservice.exception.imageException;

import com.example.messageservice.exception.commonException.error.BizException;

public class InvalidFileName extends BizException {
    public InvalidFileName(ImageErrorCode imageErrorCode) {
        super(imageErrorCode);
    }
}
