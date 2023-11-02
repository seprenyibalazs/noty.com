package com.noty.web;

@HttpStatus(code = 404)
public class NotyEntityNotFoundException extends NotyException {
    public NotyEntityNotFoundException(String message) {
        super(message);
    }
}
