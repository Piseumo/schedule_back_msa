package com.example.diaryService.exception.serviceException;

import com.example.diaryService.exception.commonException.error.ErrorCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ServiceException extends RuntimeException {

    private final ErrorCode errorCode;
    private final HttpStatus httpStatus;


    public ServiceException(String message, Throwable cause) {
        super(message, cause);
        this.errorCode = null;
        this.httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
    }

    public ServiceException(ErrorCode errorCode, Throwable cause) {
        super(errorCode.getMessage(), cause);
        this.errorCode = errorCode;
        this.httpStatus = errorCode.getHttpStatus();
    }

    public ServiceException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
        this.httpStatus = errorCode.getHttpStatus();
    }
}
