package com.sesh.demo.exceptions.custom;

public class GiftIdAlreadyExistsException extends RuntimeException {

    public GiftIdAlreadyExistsException() {
        super();
    }

    public GiftIdAlreadyExistsException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public GiftIdAlreadyExistsException(final String message) {
        super(message);
    }

    public GiftIdAlreadyExistsException(final Throwable cause) {
        super(cause);
    }
}
