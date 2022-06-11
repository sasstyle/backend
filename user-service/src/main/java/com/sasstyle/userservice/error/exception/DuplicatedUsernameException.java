package com.sasstyle.userservice.error.exception;

public class DuplicatedUsernameException extends DuplicatedException {

    public DuplicatedUsernameException() {
    }

    public DuplicatedUsernameException(String message) {
        super(message);
    }

    public DuplicatedUsernameException(String message, Throwable cause) {
        super(message, cause);
    }

}
