package com.example.diaryService.exception.imageException;

import com.example.diaryService.exception.commonException.error.BizException;

public class EmptyFileData extends BizException {
    public EmptyFileData(ImageErrorCode imageErrorCode) {
        super(imageErrorCode);
    }
}
