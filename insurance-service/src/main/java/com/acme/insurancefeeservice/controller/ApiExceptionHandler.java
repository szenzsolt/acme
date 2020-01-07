package com.acme.insurancefeeservice.controller;

import com.acme.insurancefeeservice.error.ApiErrorResponse;
import com.acme.insurancefeeservice.error.FeeNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(FeeNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleApiException(
        FeeNotFoundException ex) {
        ApiErrorResponse response =
            new ApiErrorResponse("error-0001",
                "No book found with ID " + ex.getId());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}
