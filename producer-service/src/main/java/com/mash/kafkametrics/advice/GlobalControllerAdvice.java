package com.mash.kafkametrics.advice;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalControllerAdvice {
    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(Exception e, final HttpServletRequest request) {
        ErrorResponse error = ErrorResponse.builder(e, ProblemDetail.forStatus(HttpStatus.BAD_REQUEST)).build();
        return ResponseEntity.badRequest().body(error);
    }
}
