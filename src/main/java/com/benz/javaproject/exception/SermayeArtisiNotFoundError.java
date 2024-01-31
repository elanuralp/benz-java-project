package com.benz.javaproject.exception;

public class SermayeArtisiNotFoundError extends MyException {
    public SermayeArtisiNotFoundError() {
        super("Sermaye artışı bulunamadı.", "SermayeArtisiNotFound", 5001, 500);
    }
}
