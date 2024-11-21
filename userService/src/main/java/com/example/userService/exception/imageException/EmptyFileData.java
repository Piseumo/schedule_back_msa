package com.example.userService.exception.imageException;

import com.example.userService.exception.commonException.error.BizException;

public class EmptyFileData extends BizException {
    public EmptyFileData(ImageErrorCode imageErrorCode) {
        super(imageErrorCode);
    }
}
