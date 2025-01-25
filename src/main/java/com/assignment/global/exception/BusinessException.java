package com.assignment.global.exception;

import com.assignment.global.exception.errortype.ErrorCode;

public class BusinessException extends RuntimeException{
    private final ErrorCode errorCode;

    public BusinessException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorType() {
        return errorCode;
    }
}
