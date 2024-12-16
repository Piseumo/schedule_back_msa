package com.example.messageservice.exception.diaryException;

import com.example.messageservice.exception.commonException.error.BizException;

public class InvalidCategory extends BizException {
    public InvalidCategory(DiaryErrorCode diaryErrorCode) {
        super(diaryErrorCode);
    }
}
