package com.chandrakumar.ms.api.error;

public class FieldValidationException extends RuntimeException {

    public FieldValidationException(final String msg) {
        super(msg);
    }

    public static void fieldValidationException(final String errorMessage) {
        throw new FieldValidationException(errorMessage);
    }
}
