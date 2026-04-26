package com.gathadi.ajay.ecommerce.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler  {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> methodArgumentNotValidException(MethodArgumentNotValidException manve){
        Map<String, String> errorResponse = new HashMap<>();

        manve.getBindingResult().getAllErrors().forEach(currentError -> {
            String fieldName = ((FieldError)currentError).getField();
            String errorMessage = currentError.getDefaultMessage();
            errorResponse.put(fieldName, errorMessage);
        });

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
