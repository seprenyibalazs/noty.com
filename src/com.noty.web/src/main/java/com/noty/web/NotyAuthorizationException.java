package com.noty.web;

@HttpStatus(code = 401)
public class NotyAuthorizationException extends NotyException {
    public NotyAuthorizationException(String message) {
        super(message);
    }

    public NotyAuthorizationException(String message, Throwable cause) {
        super(message, cause);
    }
}
