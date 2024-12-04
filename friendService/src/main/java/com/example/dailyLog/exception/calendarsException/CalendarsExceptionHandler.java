package com.example.dailyLog.exception.calendarsException;

import com.example.dailyLog.exception.commonException.ErrorResponse;
import com.example.dailyLog.exception.commonException.error.ErrorCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class CalendarsExceptionHandler {

    // ErrorCode를 사용하는 에러 응답 생성 메소드
    private ResponseEntity<ErrorResponse> createErrorResponse(ErrorCode errorCode) {
        return ResponseEntity.status(errorCode.getHttpStatus())
                .body(ErrorResponse.builder()
                        .message(errorCode.getMessage())
                        .httpStatus(errorCode.getHttpStatus())
                        .localDateTime(LocalDateTime.now())
                        .build());
    }


    // 캘린더 없을 때
    @ExceptionHandler(CalendarsNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleCalendarsNotFoundException(CalendarsNotFoundException e){
        return createErrorResponse(e.getErrorCode());
    }
}
