package com.example.dailyLog.exception.scheduleException;

import com.example.dailyLog.exception.commonException.error.BizException;

public class ScheduleNotFoundException extends BizException {
    public ScheduleNotFoundException(ScheduleErrorCode scheduleErrorCode) {
        super(scheduleErrorCode);
    }
}
