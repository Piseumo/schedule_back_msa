package com.example.dailyLog.exception.scheduleException;

import com.example.dailyLog.exception.commonException.error.ErrorCode;
import org.springframework.http.HttpStatus;

public enum ScheduleErrorCode implements ErrorCode {

    SCHEDULE_NOT_FOUND(HttpStatus.NOT_FOUND, "일정이 존재하지 않습니다."),
    DUPLICATE_SCHEDULE(HttpStatus.CONFLICT, "해당 시간에 중복 일정이 있습니다."),
    ;


    private final HttpStatus httpStatus;
    private final String message;

    ScheduleErrorCode(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }

    @Override
    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    @Override
    public String getMessage() {

        return message;
    }
}
