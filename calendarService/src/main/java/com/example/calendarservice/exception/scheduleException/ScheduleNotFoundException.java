package com.example.calendarservice.exception.scheduleException;

import com.example.calendarservice.exception.commonException.error.BizException;

public class ScheduleNotFoundException extends BizException {
    public ScheduleNotFoundException(ScheduleErrorCode scheduleErrorCode) {
        super(scheduleErrorCode);
    }
}
