package com.assignment.global.exception.errortype;

public enum UserErrorCode implements ErrorCode {
    USER_NOT_FOUND(404, "U001", "user not found"),
    USER_ALREADY_EXIST(400, "U002", "user already exist"),
    CANNOT_LOGIN(403, "U003", "cannot login")
    ;

    private final int status;
    private final String code;
    private final String message;

    UserErrorCode(int status, String code, String message) {
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
