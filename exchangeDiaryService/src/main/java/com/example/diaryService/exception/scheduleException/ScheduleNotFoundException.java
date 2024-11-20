package com.example.diaryService.exception.scheduleException;

import com.example.diaryService.exception.commonException.error.BizException;

public class ScheduleNotFoundException extends BizException {
    public ScheduleNotFoundException(ScheduleErrorCode scheduleErrorCode) {
        super(scheduleErrorCode);
    }
}
