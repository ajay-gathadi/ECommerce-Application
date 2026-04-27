package com.gathadi.ajay.ecommerce.exceptions;

import org.springframework.http.HttpStatus;

import java.io.Serial;

public class APIException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;

    private HttpStatus statusCode;

    public APIException() {
    }

    public APIException(String message, HttpStatus statusCode) {
        super(message);
        this.statusCode = statusCode;
    }

    public HttpStatus getStatusCode() {
        return statusCode;
    }
}
