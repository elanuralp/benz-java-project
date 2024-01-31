package com.benz.javaproject.exception;

import com.benz.javaproject.exception.MyException;

public class SenetBulunamadiError extends MyException {
    public SenetBulunamadiError() {
        super("Bu hissedara ait senet bulunmamaktadır", "SenetNotFound", 5001, 500);
    }
}