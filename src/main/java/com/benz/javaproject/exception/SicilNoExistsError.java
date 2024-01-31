package com.benz.javaproject.exception;

public class SicilNoExistsError extends MyException {
    public SicilNoExistsError() {
        super("Bu sicil numarası ile bir hissedar bulunmaktadır", "SicilNoFoundError", 5001, 500);
    }
}
