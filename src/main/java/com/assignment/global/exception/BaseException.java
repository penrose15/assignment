package com.assignment.global.exception;

import org.springframework.http.HttpStatus;

public class BaseException extends RuntimeException{
    private final HttpStatus status;
    private final ErrorType errorType;

    public BaseException(HttpStatus status, ErrorType errorType) {
        super(errorType.getMessage());
        this.status = status;
        this.errorType = errorType;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public ErrorType getErrorType() {
        return errorType;
    }
}
