package com.github.shadow.enums;

import com.github.shadow.exception.IResultCode;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResultCode implements IResultCode {
    // 错误码
    SUCCESS(200, "操作成功"),
    FAILURE(400, "业务异常"),
    INTERNAL_SERVER_ERROR(500, "服务器异常;%s"),
    ;

    private final Integer code;
    private final String message;

}
