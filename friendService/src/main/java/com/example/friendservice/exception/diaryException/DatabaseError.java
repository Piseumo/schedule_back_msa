package com.example.friendservice.exception.diaryException;

import com.example.friendservice.exception.commonException.error.BizException;

public class DatabaseError extends BizException {
    public DatabaseError(DiaryErrorCode diaryErrorCode) {
        super(diaryErrorCode);
    }
}
