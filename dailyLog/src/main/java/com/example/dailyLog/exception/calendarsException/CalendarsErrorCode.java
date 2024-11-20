package com.example.dailyLog.exception.calendarsException;

import com.example.dailyLog.exception.commonException.error.ErrorCode;
import org.springframework.http.HttpStatus;

public enum CalendarsErrorCode implements ErrorCode {

    CALENDARS_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 캘린더 ID를 찾을 수 없습니다."),
    ;

    private final HttpStatus httpStatus;
    private final String message;

    CalendarsErrorCode(HttpStatus httpStatus, String message) {
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
