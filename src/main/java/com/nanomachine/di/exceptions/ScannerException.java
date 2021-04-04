package com.nanomachine.di.exceptions;

public class ScannerException extends RuntimeException{

    public ScannerException(String message) {
        super(message);
    }

    public ScannerException(Throwable cause) {
        super(cause);
    }

    public ScannerException(String message, Throwable cause) {
        super(message, cause);
    }
}
