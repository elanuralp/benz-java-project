package com.benz.javaproject.exception;

public class SicilNoNotValidError extends MyException {
    public SicilNoNotValidError() {
        super("Sicil numarasını kontrol edin.", "SicilNoNotValidError", 5001, 500);
    }
}
