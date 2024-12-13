package com.example.dailyLog.exception.imageException;

import com.example.dailyLog.exception.commonException.ErrorResponse;
import com.example.dailyLog.exception.commonException.error.ErrorCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class ImageExceptionHandler {

    // ErrorCode를 사용하는 에러 응답 생성 메소드
    private ResponseEntity<ErrorResponse> createErrorResponse(ErrorCode errorCode) {
        return ResponseEntity.status(errorCode.getHttpStatus())
                .body(ErrorResponse.builder()
                        .message(errorCode.getMessage())
                        .httpStatus(errorCode.getHttpStatus())
                        .localDateTime(LocalDateTime.now())
                        .build());
    }


    @ExceptionHandler(InvalidUploadPath.class)
    public ResponseEntity<ErrorResponse> handleInvalidUploadPath(InvalidUploadPath e){
        return createErrorResponse(e.getErrorCode());
    }

    @ExceptionHandler(InvalidFileName.class)
    public ResponseEntity<ErrorResponse> handleInvalidFileName(InvalidFileName e){
        return createErrorResponse(e.getErrorCode());
    }

    @ExceptionHandler(EmptyFileData.class)
    public ResponseEntity<ErrorResponse> handleEmptyFileData(EmptyFileData e){
        return createErrorResponse(e.getErrorCode());
    }

    @ExceptionHandler(FailedDirectoryCreation.class)
    public ResponseEntity<ErrorResponse> handleFailedDirectoryCreation(FailedDirectoryCreation e){
        return createErrorResponse(e.getErrorCode());
    }

    @ExceptionHandler(FileWriteError.class)
    public ResponseEntity<ErrorResponse> handleFileWriteError(FileWriteError e){
        return createErrorResponse(e.getErrorCode());
    }

    @ExceptionHandler(FileUploadError.class)
    public ResponseEntity<ErrorResponse> handleFileUploadError(FileUploadError e){
        return createErrorResponse(e.getErrorCode());
    }
}
