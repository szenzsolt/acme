package com.acme.chemicalproducts.controller;

import com.acme.chemicalproducts.error.ApiErrorResponse;
import com.acme.chemicalproducts.error.FeeNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(FeeNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleApiException(
        FeeNotFoundException ex) {
        ApiErrorResponse response =
            new ApiErrorResponse("Error",
                String.format("No fee found for the given product %s in the following category: %s",
                    ex.getProductName(), ex.getCategory()));
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorResponse> handleValidationException(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        ApiErrorResponse response =
            new ApiErrorResponse("Validation Error","The provided request was not valid: " + errors.toString());

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
