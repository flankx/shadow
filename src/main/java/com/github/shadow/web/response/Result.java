package com.github.shadow.web.response;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result<T> {

    private int code;
    private String message;
    private T data;

    public static <T> Result<T> success(T data) {
        return new Result<>(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), data);
    }

    public static <T> Result<T> error(ErrorCode errorCode) {
        return new Result<>(errorCode.getCode(), errorCode.getMessage(), null);
    }

    public static <T> Result<T> error(ErrorCode errorCode, String... args) {
        return new Result<>(errorCode.getCode(), String.format(errorCode.getMessage(), args), null);
    }

    public static <T> Result<T> error(Integer errorCode, String errorMessage) {
        return new Result<>(errorCode, errorMessage, null);
    }
}
