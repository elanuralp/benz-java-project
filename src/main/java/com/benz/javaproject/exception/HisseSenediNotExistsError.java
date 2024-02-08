package com.benz.javaproject.exception;

public class HisseSenediNotExistsError extends MyException {
    public HisseSenediNotExistsError() {
        super("Böyle bir hisse senedi bulunmamaktadır.", "HisseSenediNotFound", 5001, 500);
    }
}