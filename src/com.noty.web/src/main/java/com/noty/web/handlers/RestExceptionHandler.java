package com.noty.web.handlers;

import com.noty.web.NotyValidationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {Exception.class})
    protected ResponseEntity<?> handleConflict(Exception ex, WebRequest request) {
        if (ex instanceof NotyValidationException) {
            return statusResult(400, ex);

        } else {
            return statusResult(500, ex);
            
        }
    }

    private ResponseEntity<?> statusResult(int statusCode, Exception ex) {
        Map<String, Object> body = new HashMap<>();
        body.put("message", ex.getMessage());

        return ResponseEntity.status(statusCode).body(body);
    }
}
