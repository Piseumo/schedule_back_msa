package com.example.dailyLog.exception.userException;

import com.example.dailyLog.exception.commonException.error.ErrorCode;
import org.springframework.http.HttpStatus;

public enum UserErrorCode implements ErrorCode {

    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "유저가 존재하지 않습니다."),
    VALIDATION_ERROR(HttpStatus.BAD_REQUEST, "유효성 검사 실패"),
    ;


    private final HttpStatus httpStatus;
    private final String message;

    UserErrorCode(HttpStatus httpStatus, String message) {
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
