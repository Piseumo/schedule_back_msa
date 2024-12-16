package com.example.friendservice.exception.imageException;

import com.example.friendservice.exception.commonException.error.BizException;

public class EmptyFileData extends BizException {
    public EmptyFileData(ImageErrorCode imageErrorCode) {
        super(imageErrorCode);
    }
}
