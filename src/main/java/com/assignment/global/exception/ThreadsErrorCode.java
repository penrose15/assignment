package com.assignment.global.exception;

public enum ThreadsErrorCode implements ErrorType{
    THREAD_NOT_FOUND(404, "T001", "thread not found")
    ;

    private final int status;
    private final String code;
    private final String message;

    ThreadsErrorCode(int status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }

    @Override
    public int getStatus() {
        return this.status;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}
