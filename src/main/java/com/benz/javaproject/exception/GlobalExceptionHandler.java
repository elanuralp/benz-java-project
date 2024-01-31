package com.benz.javaproject.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    public record ExceptionResponse(String exceptionMessage, String exceptionName, int code) {}

    @ExceptionHandler({MyException.class,
            SicilNoExistsError.class,
            HissedarNotExistsError.class})
    public ResponseEntity<ExceptionResponse> handleError(MyException exception) {
        return ResponseEntity.status(exception.getHttpCode()).body(new ExceptionResponse(exception.getMessage(), exception.getName(), exception.getCode()));
    }
}
