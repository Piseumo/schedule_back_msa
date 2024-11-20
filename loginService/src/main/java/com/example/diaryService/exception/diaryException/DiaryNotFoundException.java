package com.example.diaryService.exception.diaryException;

import com.example.diaryService.exception.commonException.error.BizException;

public class DiaryNotFoundException extends BizException {
    public DiaryNotFoundException(DiaryErrorCode diaryErrorCode) {
        super(diaryErrorCode);
    }
}
