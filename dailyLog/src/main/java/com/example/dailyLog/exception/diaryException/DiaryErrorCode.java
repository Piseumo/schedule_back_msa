package com.example.dailyLog.exception.diaryException;

import com.example.dailyLog.exception.commonException.error.ErrorCode;
import org.springframework.http.HttpStatus;

public enum DiaryErrorCode implements ErrorCode {

    DIARY_NOT_FOUND(HttpStatus.NOT_FOUND, "다이어리가 존재하지 않습니다."),
    INVALID_DIARY_DATE(HttpStatus.BAD_REQUEST, "잘못된 날짜 형식입니다."),
    DIARY_PERMISSION_DENIED(HttpStatus.FORBIDDEN, "다이어리에 대한 접근 권한이 없습니다."),
    DUPLICATE_Diary(HttpStatus.CONFLICT, "해당 시간에 중복 일기가 있습니다."),
    INVALID_CATEGORY(HttpStatus.BAD_REQUEST, "유효하지 않은 카테고리 값입니다."),
    DATABASE_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "일기 데이터베이스에서 오류가 발생했습니다."),
    ;


    private final HttpStatus httpStatus;
    private final String message;

    DiaryErrorCode(HttpStatus httpStatus, String message) {
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
