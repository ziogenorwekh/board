package com.portfolio.boardproject.exception;

public class NotOwnerException extends RuntimeException {
    public NotOwnerException(String message) {
        super(message);
    }

    public NotOwnerException(String message, Throwable cause) {
        super(message, cause);
    }
}
