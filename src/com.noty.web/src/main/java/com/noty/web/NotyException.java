package com.noty.web;

public class NotyException extends Exception {

    public NotyException() {
    }

    public NotyException(String message) {
        super(message);
    }

    public NotyException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotyException(Throwable cause) {
        super(cause);
    }

    public NotyException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
