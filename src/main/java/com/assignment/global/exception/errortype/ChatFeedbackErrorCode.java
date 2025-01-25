package com.assignment.global.exception.errortype;

public enum ChatFeedbackErrorCode implements ErrorCode{
    CHAT_FEEDBACK_ALREADY_EXIST(400, "CF001", "Chat feedback already exists")
    ;

    private final int status;
    private final String code;
    private final String message;

    ChatFeedbackErrorCode(int status, String code, String message) {
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
