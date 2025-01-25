package com.assignment.global.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.io.IOException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<?> handleMethodArgumentNotValidExcepiton(MethodArgumentNotValidException e) {
        log.error("MethodArgumentNotValidException", e);
        ErrorResponse errorResponse = ErrorResponse.of(GlobalErrorCode.INVALID_REQUEST, e.getBindingResult());

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MissingRequestHeaderException.class)
    protected ResponseEntity<?> handleMissingRequestHeaderException(MissingRequestHeaderException e) {
        log.error("MissingRequestHeaderException", e);
        ErrorResponse errorResponse = ErrorResponse.of(GlobalErrorCode.HEADER_NOT_FOUND, e.getMessage());

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    protected ResponseEntity<ErrorResponse> handleMissingRequestHeaderExceptionException(
            MissingServletRequestParameterException ex) {
        log.error("handleMissingServletRequestParameterException", ex);
        final ErrorResponse response = ErrorResponse.of(GlobalErrorCode.MISSING_REQUEST_PARAMETER_ERROR, ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    protected ResponseEntity<?> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        log.error("HttpMessageNotReadableException", e);
        ErrorResponse errorResponse = ErrorResponse.of(GlobalErrorCode.REQUEST_BODY_MISSING, e.getMessage());

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpClientErrorException.BadRequest.class)
    protected ResponseEntity<?> handleBadRequestException(HttpClientErrorException e) {
        log.error("BadRequestException", e);
        ErrorResponse errorResponse = ErrorResponse.of(GlobalErrorCode.BAD_REQUEST, e.getMessage());

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    protected ResponseEntity<?> handleNoHandlerFoundException(NoHandlerFoundException e) {
        log.error("NoHandlerFoundException", e);
        ErrorResponse errorResponse = ErrorResponse.of(GlobalErrorCode.NOT_FOUND, e.getMessage());

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IOException.class)
    protected ResponseEntity<?> handleIOException(IOException e) {
        log.error("IOException", e);
        ErrorResponse errorResponse = ErrorResponse.of(GlobalErrorCode.IO_EXCEPTION, e.getMessage());

        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(BusinessException.class)
    protected ResponseEntity<?> handleBaseException(BusinessException e) {
        log.error("business exception",e);
        ErrorResponse errorResponse = ErrorResponse.of(e.getErrorType());

        return ResponseEntity.status(e.getErrorType().getStatus())
                .body(errorResponse);
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<?> handleException(Exception e) {
        log.error("internal server error", e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
}
