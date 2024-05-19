package com.mash.kafkametrics.controller.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * Global controller advice to handle various exceptions when interacting with the rest controller.
 *
 * @author Mikhail Shamanov
 */
@ControllerAdvice
public class GlobalControllerAdvice extends ResponseEntityExceptionHandler {
    /**
     * Handles {@link ResponseStatusException}.
     *
     * @param e instance of exception
     * @return response
     */
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<?> handleResponseException(ResponseStatusException e) {
        return ResponseEntity.of(e.getBody()).build();
    }

    /**
     * Handles any exception.
     *
     * @param e instance of exception
     * @return response
     */
    @ExceptionHandler
    public ResponseEntity<?> handleAnyException(Exception e) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, e.getLocalizedMessage());
        return ResponseEntity.of(problemDetail).build();
    }
}
