package com.example.messageservice.exception.diaryException;

import com.example.messageservice.exception.commonException.error.BizException;

public class DatabaseError extends BizException {
    public DatabaseError(DiaryErrorCode diaryErrorCode) {
        super(diaryErrorCode);
    }
}
