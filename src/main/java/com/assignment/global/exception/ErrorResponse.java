package com.assignment.global.exception;

import com.assignment.global.exception.errortype.ErrorCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.validation.BindingResult;

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

    public static ErrorResponse of(ErrorCode errorCode, BindingResult bindingResult) {
        return new ErrorResponse(errorCode.getCode(), FieldError.of(bindingResult), errorCode.getMessage());
    }

    public static ErrorResponse of(ErrorCode errorCode, String message) {
        return new ErrorResponse(errorCode.getCode(), null, message);
    }

    public static ErrorResponse of(ErrorCode errorCode) {
        return new ErrorResponse(errorCode.getCode(), null, errorCode.getMessage());
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
