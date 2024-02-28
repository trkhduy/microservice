package com.test.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

@RestControllerAdvice
@ControllerAdvice
public class ExceptionHandlerAdvice {

    /**
     * Handles ResponseStatusException and returns a 400 Bad Request response.
     *
     * @param ex The ResponseStatusException instance.
     * @return ResponseEntity containing message with the error details.
     */
    @ExceptionHandler(ResponseStatusException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<Object> handleResponseStatusException(ResponseStatusException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.UNAUTHORIZED);
    }
}
