package com.sesh.demo.exceptions.custom;

public class GiftIdMismatchException extends RuntimeException {

    public GiftIdMismatchException() {
        super();
    }

    public GiftIdMismatchException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public GiftIdMismatchException(final String message) {
        super(message);
    }

    public GiftIdMismatchException(final Throwable cause) {
        super(cause);
    }
}
