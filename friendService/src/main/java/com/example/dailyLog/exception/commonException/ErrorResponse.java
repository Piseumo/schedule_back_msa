package com.example.dailyLog.exception.commonException;

import lombok.*;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
@Builder
public class ErrorResponse {

    private final HttpStatus httpStatus;
    private final String message;
    private final LocalDateTime localDateTime;
}
