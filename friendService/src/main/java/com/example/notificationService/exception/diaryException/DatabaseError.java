package com.example.notificationService.exception.diaryException;

import com.example.notificationService.exception.commonException.error.BizException;

public class DatabaseError extends BizException {
    public DatabaseError(DiaryErrorCode diaryErrorCode) {
        super(diaryErrorCode);
    }
}
