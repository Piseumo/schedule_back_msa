package com.example.userservice.exception.diaryException;

import com.example.userservice.exception.commonException.error.BizException;

public class DatabaseError extends BizException {
    public DatabaseError(DiaryErrorCode diaryErrorCode) {
        super(diaryErrorCode);
    }
}
