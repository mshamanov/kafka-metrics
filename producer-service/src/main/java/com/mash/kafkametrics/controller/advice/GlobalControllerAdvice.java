package com.mash.kafkametrics.controller.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Global controller advice to handle various exceptions when interacting with the rest controller.
 *
 * @author Mikhail Shamanov
 */
@ControllerAdvice
public class GlobalControllerAdvice {
    /**
     * Handles exceptions when validation on an argument annotated with @Valid fails.
     *
     * @param e instance of exception
     * @return response
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> validationError(MethodArgumentNotValidException e) {
        BindingResult result = e.getBindingResult();

        List<FieldError> fieldErrors = result.getFieldErrors();
        String errorsAsString = fieldErrors.stream()
                .map(error -> "Error: %s - %s".formatted(error.getField(), error.getDefaultMessage()))
                .collect(Collectors.joining(System.lineSeparator()));

        ProblemDetail body = e.getBody();
        body.setDetail(errorsAsString);

        return ResponseEntity.badRequest().body(body);
    }

    /**
     * Handles any exception.
     *
     * @param e instance of exception
     * @return response
     */
    @ExceptionHandler
    public ResponseEntity<?> handleException(Exception e) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, e.getLocalizedMessage());
        return ResponseEntity.badRequest().body(problemDetail);
    }
}
