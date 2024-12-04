package com.example.userservice.exception.diaryException;

import com.example.userservice.exception.commonException.error.BizException;

public class InvalidCategory extends BizException {
    public InvalidCategory(DiaryErrorCode diaryErrorCode) {
        super(diaryErrorCode);
    }
}
