package com.assignment.global.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class ErrorResponse {
    private String code;
    private List<FieldError> fieldErrors;
    private String resultMessage;

    private ErrorResponse(String code, List<FieldError> fieldErrors, String resultMessage) {
        this.code = code;
        this.fieldErrors = fieldErrors;
        this.resultMessage = resultMessage;
    }

    public static ErrorResponse of(ErrorType errorType, BindingResult bindingResult) {
        return new ErrorResponse(errorType.getCode(), FieldError.of(bindingResult), errorType.getMessage());
    }

    public static ErrorResponse of(ErrorType errorType, String message) {
        return new ErrorResponse(errorType.getCode(), null, message);
    }

    public static ErrorResponse of(ErrorType errorType) {
        return new ErrorResponse(errorType.getCode(), null, errorType.getMessage());
    }

    @Getter
    public static class FieldError {
        private final String field;
        private final String value;
        private final String reason;

        public static List<FieldError> of(BindingResult bindingResult) {
            List<org.springframework.validation.FieldError> fieldErrors = bindingResult.getFieldErrors();

            List<FieldError> result = new ArrayList<>();
            for(org.springframework.validation.FieldError fieldError : fieldErrors) {
                result.add(new FieldError(
                        fieldError.getField(),
                        fieldError.getRejectedValue() == null ? "" : fieldError.getRejectedValue().toString(),
                        fieldError.getDefaultMessage()
                ));
            }
            return result;
        }

        private FieldError(String field, String value, String reason) {
            this.field = field;
            this.value = value;
            this.reason = reason;
        }
    }
}
