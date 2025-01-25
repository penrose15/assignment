package com.assignment.global.exception.errortype;

public enum GlobalErrorCode implements ErrorCode {
    INVALID_REQUEST(400, "G001", "handle Validation Exception"),
    HEADER_NOT_FOUND(400, "G002", "header not found"),
    IO_EXCEPTION(500, "G003", "io Exception"),
    FORBIDDEN(403, "G004", "forbidden exception"),
    NOT_FOUND(404, "G005", "not found"),
    INVALID_HEADER(400, "G006", "invalid header"),
    REQUEST_BODY_MISSING(400, "G007", "request body missing"),
    BAD_REQUEST(400, "G008", "bad request"),
    MISSING_REQUEST_PARAMETER_ERROR(400, "G009", "parameter not found"),
    INTERNAL_SERVER_ERROR(500, "G100", "internal server error");


    private int status;
    private String code;
    private String message;

    GlobalErrorCode(int status, String code, String message) {
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
