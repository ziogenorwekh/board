package com.portfolio.boardproject.exception;

public class UserEmailDuplicatedException extends RuntimeException {

    public UserEmailDuplicatedException(String message) {
        super(message);
    }

    public UserEmailDuplicatedException(String message, Throwable cause) {
        super(message, cause);
    }
}
