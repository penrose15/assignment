package com.assignment.global.exception;

public interface ErrorType {
    int getStatus();

    String getCode();

    String getMessage();
}
