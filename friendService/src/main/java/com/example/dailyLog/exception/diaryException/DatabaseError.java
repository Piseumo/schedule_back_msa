package com.example.dailyLog.exception.diaryException;

import com.example.dailyLog.exception.commonException.error.BizException;

public class DatabaseError extends BizException {
    public DatabaseError(DiaryErrorCode diaryErrorCode) {
        super(diaryErrorCode);
    }
}
