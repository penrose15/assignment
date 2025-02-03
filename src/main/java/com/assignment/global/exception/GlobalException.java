package com.assignment.global.exception;

import com.assignment.global.exception.errortype.ErrorCode;

public class GlobalException extends RuntimeException {
    private final ErrorCode errorCode;


    public GlobalException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
