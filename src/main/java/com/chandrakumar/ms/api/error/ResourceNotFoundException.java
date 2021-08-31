package com.chandrakumar.ms.api.error;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(final String msg) {
        super(msg);
    }
}
