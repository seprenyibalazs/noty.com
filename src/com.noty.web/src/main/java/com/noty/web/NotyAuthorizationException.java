package com.noty.web;

@HttpStatus(code = 402)
public class NotyAuthorizationException extends NotyException {
    public NotyAuthorizationException(String message) {
        super(message);
    }
}
