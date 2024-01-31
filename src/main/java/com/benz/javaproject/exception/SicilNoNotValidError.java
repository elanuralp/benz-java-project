package com.benz.javaproject.exception;

public class SicilNoNotValidError extends MyException {
    public SicilNoNotValidError() {
        super("Sicil numarası 0 ile başlayamaz", "SicilNoNotValidError", 5001, 500);
    }
}
