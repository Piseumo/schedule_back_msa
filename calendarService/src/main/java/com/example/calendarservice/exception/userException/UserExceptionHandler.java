package com.example.calendarservice.exception.userException;

import com.example.calendarservice.exception.commonException.ErrorResponse;
import com.example.calendarservice.exception.commonException.error.ErrorCode;
import com.example.calendarservice.exception.userException.UserNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class UserExceptionHandler {

    // ErrorCode를 사용하는 에러 응답 생성 메소드
    private ResponseEntity<ErrorResponse> createErrorResponse(ErrorCode errorCode) {
        return ResponseEntity.status(errorCode.getHttpStatus())
                .body(ErrorResponse.builder()
                        .message(errorCode.getMessage())
                        .httpStatus(errorCode.getHttpStatus())
                        .localDateTime(LocalDateTime.now())
                        .build());
    }


    // 유저 없을 때
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFoundException(UserNotFoundException e){
        return createErrorResponse(e.getErrorCode());
    }

    @ExceptionHandler(ValidationError.class)
    public ResponseEntity<ErrorResponse> handleValidationError(ValidationError e){
        return createErrorResponse(e.getErrorCode());
    }
}
