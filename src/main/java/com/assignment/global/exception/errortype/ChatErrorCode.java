package com.assignment.global.exception.errortype;

public enum ChatErrorCode implements ErrorCode {
    CHAT_NOT_FOUND(404, "C001", "chat not found");

    private final int status;
    private final String code;
    private final String message;

    ChatErrorCode(int status, String code, String message) {
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
