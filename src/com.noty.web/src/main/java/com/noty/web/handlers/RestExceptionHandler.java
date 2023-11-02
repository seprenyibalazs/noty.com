package com.noty.web.handlers;

import com.noty.web.HttpStatus;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    protected final Log logger = LogFactory.getLog(getClass());

    @ExceptionHandler(value = {Exception.class})
    protected ResponseEntity<?> handleConflict(Exception ex, WebRequest request) {
        HttpStatus statusAnnotation = ex.getClass().getAnnotation(HttpStatus.class);
        if (statusAnnotation != null)
            return statusResult(statusAnnotation.code(), ex);
        else {
            logger.error("Unhandled exception in REST request.", ex);
            return statusResult(500, ex);
        }
    }

    private ResponseEntity<?> statusResult(int statusCode, Exception ex) {
        Map<String, Object> body = new HashMap<>();
        body.put("message", ex.getMessage());

        return ResponseEntity.status(statusCode).body(body);
    }
}
