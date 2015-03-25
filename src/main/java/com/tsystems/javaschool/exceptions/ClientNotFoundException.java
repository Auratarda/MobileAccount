package com.tsystems.javaschool.exceptions;

/**
 * ClientNotFound - login exception.
 */
public class ClientNotFoundException extends Exception {
    public ClientNotFoundException() {

    }

    public ClientNotFoundException(String message) {
        super(message);
    }
}
