package com.example.dailyLog.exception.commonException;

import com.example.dailyLog.exception.commonException.error.ErrorCode;
import org.springframework.http.HttpStatus;

public enum CommonErrorCode implements ErrorCode {

    // 시스템 예외
    DATABASE_CONNECTION_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "데이터베이스 연결에 실패했습니다."),
    DATABASE_INTEGRITY_ERROR(HttpStatus.CONFLICT, "Database integrity error"),
    FILE_SAVE_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "파일 저장 중 오류가 발생했습니다."),
    JSON_PARSING_ERROR(HttpStatus.BAD_REQUEST, "요청 데이터의 형식이 잘못되었습니다."),
    EXTERNAL_API_ERROR(HttpStatus.BAD_GATEWAY, "외부 API 호출에 실패했습니다."),
    SERVER_TIMEOUT(HttpStatus.REQUEST_TIMEOUT, "서버 응답 시간이 초과되었습니다."),
    ACCESS_DENIED(HttpStatus.FORBIDDEN, "접근이 허용되지 않습니다."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "내부 서버 오류가 발생했습니다."),

    // 비즈니스 로직 예외
    NOT_FOUND(HttpStatus.BAD_REQUEST, "해당하는 글이 없습니다."),

    UNAUTHORIZED_ACCESS(HttpStatus.UNAUTHORIZED, "권한이 없습니다."),
    INVALID_YEAR(HttpStatus.BAD_REQUEST, "잘못된 연도의 범위입니다."),
    INVALID_MONTH(HttpStatus.BAD_REQUEST, "잘못된 달(월)의 범위입니다. 1 ~ 12 사이여야 합니다."),
    INVALID_DAY(HttpStatus.BAD_REQUEST, "해당 월의 잘못된 일(day) 범위입니다."),
    RESOURCE_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 리소스를 찾을 수 없습니다."),
    INVALID_TIME_FORMAT(HttpStatus.BAD_REQUEST, "잘못된 시간 형식입니다."),

    ;

    private final HttpStatus httpStatus;
    private final String message;

    CommonErrorCode(HttpStatus httpStatus, String message) {
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
