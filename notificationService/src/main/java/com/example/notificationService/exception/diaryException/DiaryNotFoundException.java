package com.example.notificationService.exception.diaryException;

import com.example.notificationService.exception.commonException.error.BizException;

public class DiaryNotFoundException extends BizException {
    public DiaryNotFoundException(DiaryErrorCode diaryErrorCode) {
        super(diaryErrorCode);
    }
}
