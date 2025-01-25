package com.assignment.global.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {
    private int status;
    private String code;
    private T data;
    private String message;

    public ApiResponse(int status, String code, T data, String message) {
        this.status = status;
        this.code = code;
        this.data = data;
        this.message = message;
    }

    public static <T> ApiResponse<T> success(String code, T data) {
        return new ApiResponse<>(200, code, data, "success");
    }
}
