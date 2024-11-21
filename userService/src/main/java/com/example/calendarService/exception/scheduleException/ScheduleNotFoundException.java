package com.example.calendarService.exception.scheduleException;

import com.example.calendarService.exception.commonException.error.BizException;

public class ScheduleNotFoundException extends BizException {
    public ScheduleNotFoundException(ScheduleErrorCode scheduleErrorCode) {
        super(scheduleErrorCode);
    }
}
