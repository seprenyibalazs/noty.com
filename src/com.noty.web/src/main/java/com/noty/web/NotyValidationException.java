package com.noty.web;

import com.noty.web.NotyException;

public class NotyValidationException extends NotyException {

    public NotyValidationException() {
    }

    public NotyValidationException(String message) {
        super(message);
    }

    public NotyValidationException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotyValidationException(Throwable cause) {
        super(cause);
    }

    public NotyValidationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
