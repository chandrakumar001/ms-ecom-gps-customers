package com.chandrakumar.ms.api.error;

public class HttpHeaderException extends RuntimeException {

    public HttpHeaderException(final String message) {
        super(message);
    }

    public static void httpHeaderExceptionException(final String errorMessage) {
        throw new HttpHeaderException(errorMessage);
    }
}
