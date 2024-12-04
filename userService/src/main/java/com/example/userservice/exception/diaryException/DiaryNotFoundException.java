package com.example.userservice.exception.diaryException;

import com.example.userservice.exception.commonException.error.BizException;

public class DiaryNotFoundException extends BizException {
    public DiaryNotFoundException(DiaryErrorCode diaryErrorCode) {
        super(diaryErrorCode);
    }
}
