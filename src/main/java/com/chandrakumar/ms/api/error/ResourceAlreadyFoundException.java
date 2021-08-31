package com.chandrakumar.ms.api.error;

public class ResourceAlreadyFoundException extends RuntimeException {

    public ResourceAlreadyFoundException(final String msg) {
        super(msg);
    }
}
