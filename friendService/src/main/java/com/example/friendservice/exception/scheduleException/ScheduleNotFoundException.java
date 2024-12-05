package com.example.friendservice.exception.scheduleException;

import com.example.friendservice.exception.commonException.error.BizException;

public class ScheduleNotFoundException extends BizException {
    public ScheduleNotFoundException(ScheduleErrorCode scheduleErrorCode) {
        super(scheduleErrorCode);
    }
}
