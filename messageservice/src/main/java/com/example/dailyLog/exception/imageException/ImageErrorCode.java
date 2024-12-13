package com.example.dailyLog.exception.imageException;

import com.example.dailyLog.exception.commonException.error.ErrorCode;
import org.springframework.http.HttpStatus;

public enum ImageErrorCode implements ErrorCode {

    INVALID_UPLOAD_PATH(HttpStatus.BAD_REQUEST, "업로드 경로가 유효하지 않습니다."),
    INVALID_FILE_NAME(HttpStatus.BAD_REQUEST, "유효하지 않은 파일 이름입니다."),
    EMPTY_FILE_DATA(HttpStatus.BAD_REQUEST, "파일 데이터가 비어 있습니다."),
    FAILED_DIRECTORY_CREATION(HttpStatus.INTERNAL_SERVER_ERROR, "파일 업로드 경로를 생성할 수 없습니다."),
    FILE_WRITE_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "파일을 쓰는 도중 오류가 발생했습니다."),
    FILE_UPLOAD_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "파일 업로드 중 오류가 발생했습니다."),
    ;

    private final HttpStatus httpStatus;
    private final String message;

    ImageErrorCode(HttpStatus httpStatus, String message) {
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
