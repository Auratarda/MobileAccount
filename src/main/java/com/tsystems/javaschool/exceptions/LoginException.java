package com.tsystems.javaschool.exceptions;

/**
 * ClientNotFound - login exception.
 */
public class LoginException extends Exception {
    public LoginException() {

    }

    public LoginException(String message) {
        super(message);
    }
}
