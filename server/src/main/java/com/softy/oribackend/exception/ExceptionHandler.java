package com.softy.oribackend.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * Note that it cannot catch exception thrown via websocket.
 */
@ControllerAdvice
public class ExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(ExceptionHandler.class);

    @org.springframework.web.bind.annotation.ExceptionHandler(value = {
            RuntimeException.class
    })
    protected ResponseEntity<?> handle(RuntimeException exception) {
        log.error(exception.getMessage());

        final var body = exception.getMessage();

        return ResponseEntity.badRequest().body(body);
    }

}
