package com.noty.web;

@HttpStatus(code = 403)
public class NotyForbiddenException extends NotyException {

    public NotyForbiddenException(String message) {
        super(message);
    }
}
