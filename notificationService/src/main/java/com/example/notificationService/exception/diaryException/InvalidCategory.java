package com.example.notificationService.exception.diaryException;

import com.example.notificationService.exception.commonException.error.BizException;

public class InvalidCategory extends BizException {
    public InvalidCategory(DiaryErrorCode diaryErrorCode) {
        super(diaryErrorCode);
    }
}
