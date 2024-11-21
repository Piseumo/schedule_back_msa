package com.example.userService.exception.imageException;

import com.example.userService.exception.commonException.error.BizException;

public class FailedDirectoryCreation extends BizException {
    public FailedDirectoryCreation(ImageErrorCode imageErrorCode) {
        super(imageErrorCode);
    }
}
