package com.example.messageservice.exception.diaryException;

import com.example.messageservice.exception.commonException.ErrorResponse;
import com.example.messageservice.exception.commonException.error.ErrorCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class DiaryExceptionHandler {

    // ErrorCode를 사용하는 에러 응답 생성 메소드
    private ResponseEntity<ErrorResponse> createErrorResponse(ErrorCode errorCode) {
        return ResponseEntity.status(errorCode.getHttpStatus())
                .body(ErrorResponse.builder()
                        .message(errorCode.getMessage())
                        .httpStatus(errorCode.getHttpStatus())
                        .localDateTime(LocalDateTime.now())
                        .build());
    }


    // 일기가 없을 때
    @ExceptionHandler(DiaryNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleDiaryNotFoundException(DiaryNotFoundException e){
        return createErrorResponse(e.getErrorCode());
    }

    @ExceptionHandler(InvalidCategory.class)
    public ResponseEntity<ErrorResponse> handleInvalidCategory(InvalidCategory e){
        return createErrorResponse(e.getErrorCode());
    }

    @ExceptionHandler(DatabaseError.class)
    public ResponseEntity<ErrorResponse> handleDatabaseError(DatabaseError e){
        return createErrorResponse(e.getErrorCode());
    }
}
