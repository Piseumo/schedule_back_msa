package com.example.diaryService.exception.diaryException;

import com.example.diaryService.exception.commonException.error.BizException;

public class InvalidCategory extends BizException {
    public InvalidCategory(DiaryErrorCode diaryErrorCode) {
        super(diaryErrorCode);
    }
}
