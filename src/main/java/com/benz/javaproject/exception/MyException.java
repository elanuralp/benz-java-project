package com.benz.javaproject.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
public class MyException extends RuntimeException {
    private final String message;
    private final String name;
    private final int code;
    private final int httpCode;
}
