package com.example.dailyLog.exception.loginException;

import com.example.dailyLog.exception.commonException.error.ErrorCode;
import com.example.dailyLog.exception.commonException.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class LoginExceptionHandler {

    // ErrorCode를 사용하는 에러 응답 생성 메소드
    private ResponseEntity<ErrorResponse> createErrorResponse(ErrorCode errorCode) {
        return ResponseEntity.status(errorCode.getHttpStatus())
                .body(ErrorResponse.builder()
                        .message(errorCode.getMessage())
                        .httpStatus(errorCode.getHttpStatus())
                        .localDateTime(LocalDateTime.now())
                        .build());
    }


    @ExceptionHandler(DuplicateEmailException.class)
    public ResponseEntity<ErrorResponse> handleDuplicateEmailException(DuplicateEmailException e){
        return createErrorResponse(e.getErrorCode());
    }

    @ExceptionHandler(DuplicateUserNameException.class)
    public ResponseEntity<ErrorResponse> handleDuplicateUserNameException(DuplicateUserNameException e){
        return createErrorResponse(e.getErrorCode());
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<ErrorResponse> handleInvalidCredentialsException(InvalidCredentialsException e){
        return createErrorResponse(e.getErrorCode());
    }

    @ExceptionHandler(EmailNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleEmailNotFoundException(EmailNotFoundException e){
        return createErrorResponse(e.getErrorCode());
    }

    @ExceptionHandler(UserPKException.class)
    public ResponseEntity<ErrorResponse> handleUserPKException(UserPKException e){
        return createErrorResponse(e.getErrorCode());
    }

    @ExceptionHandler(HttpRequestException.class)
    public ResponseEntity<ErrorResponse> handleHttpRequestException(HttpRequestException e){
        return createErrorResponse(e.getErrorCode());
    }
}
