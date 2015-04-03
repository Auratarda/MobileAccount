package com.tsystems.javaschool.service.exceptions;

/**
 * ClientNotFound - login exception.
 */
public class TariffNotSupportedOptionException extends Exception {
    public TariffNotSupportedOptionException() {

    }

    public TariffNotSupportedOptionException(String message) {
        super(message);
    }
}
