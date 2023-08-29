package com.github.shadow.pojo;

import org.springframework.http.HttpStatus;

import com.github.shadow.exception.ErrorCode;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class R<T> {

    private int code;
    private String message;
    private T data;

    public static <T> R<T> success(T data) {
        return new R<>(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), data);
    }

    public static <T> R<T> error(ErrorCode errorCode) {
        return new R<>(errorCode.getCode(), errorCode.getMessage(), null);
    }

    public static <T> R<T> error(ErrorCode errorCode, String... args) {
        return new R<>(errorCode.getCode(), String.format(errorCode.getMessage(), args), null);
    }

    public static <T> R<T> error(Integer errorCode, String errorMessage) {
        return new R<>(errorCode, errorMessage, null);
    }
}
