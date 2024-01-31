package com.benz.javaproject.exception;

public class HissedarNotExistsError extends MyException {
    public HissedarNotExistsError() {
        super("Bir hissedar bulunmamaktadır", "HissedarNotFound", 5001, 500);
    }
}