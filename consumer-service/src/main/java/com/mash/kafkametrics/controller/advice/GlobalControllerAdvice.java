package com.mash.kafkametrics.controller.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Global controller advice to handle various exceptions when interacting with the rest controller.
 *
 * @author Mikhail Shamanov
 */
@ControllerAdvice
public class GlobalControllerAdvice {
    @ExceptionHandler
    public ResponseEntity<?> handleException(Exception e) {
        ErrorResponse error = ErrorResponse.builder(e, HttpStatus.BAD_REQUEST, e.getLocalizedMessage()).build();
        return ResponseEntity.badRequest().body(error.getBody());
    }
}
