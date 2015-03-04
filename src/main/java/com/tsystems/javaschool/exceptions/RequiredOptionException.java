package com.tsystems.javaschool.exceptions;

/**
 * IncompatibleOptionException.
 */
public class RequiredOptionException extends Exception {
    public RequiredOptionException() {

    }

    public RequiredOptionException(String message) {
        super(message);
    }
}
