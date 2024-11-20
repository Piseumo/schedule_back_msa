package com.example.dailyLog.exception.serviceException;

import com.example.dailyLog.exception.commonException.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class ServiceExceptionHandler {

    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<ErrorResponse> handleServiceException(ServiceException e) {

        String message = (e.getErrorCode() != null) ? e.getErrorCode().getMessage() : e.getMessage();
        HttpStatus status = (e.getErrorCode() != null) ? e.getErrorCode().getHttpStatus() : e.getHttpStatus();

        ErrorResponse errorResponse = ErrorResponse.builder()
                .message(message)
                .httpStatus(status)
                .localDateTime(LocalDateTime.now())
                .build();

        return ResponseEntity.status(status).body(errorResponse);
    }
}
