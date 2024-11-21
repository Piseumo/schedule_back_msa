package com.example.userService.exception.imageException;

import com.example.userService.exception.commonException.error.BizException;

public class InvalidUploadPath extends BizException {
    public InvalidUploadPath(ImageErrorCode imageErrorCode) {
        super(imageErrorCode);
    }
}
