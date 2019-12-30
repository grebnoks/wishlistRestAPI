package com.sesh.demo.exceptions.custom;

public class GiftNotFoundException extends RuntimeException {

    public GiftNotFoundException() {
        super();
    }

    public GiftNotFoundException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public GiftNotFoundException(final String message) {
        super(message);
    }

    public GiftNotFoundException(final Throwable cause) {
        super(cause);
    }
}
