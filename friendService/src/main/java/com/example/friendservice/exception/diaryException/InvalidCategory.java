package com.example.friendservice.exception.diaryException;

import com.example.friendservice.exception.commonException.error.BizException;

public class InvalidCategory extends BizException {
    public InvalidCategory(DiaryErrorCode diaryErrorCode) {
        super(diaryErrorCode);
    }
}
