package com.chandrakumar.ms.api.error;

public class NoRecordFoundException extends RuntimeException {

    public NoRecordFoundException(final String msg) {
        super(msg);
    }
}
