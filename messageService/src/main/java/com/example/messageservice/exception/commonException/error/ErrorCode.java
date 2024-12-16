package com.example.messageservice.exception.commonException.error;

import org.springframework.http.HttpStatus;

public interface ErrorCode {

    HttpStatus getHttpStatus();
    String getMessage();
}
