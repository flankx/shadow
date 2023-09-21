package com.github.shadow.exception;

import com.github.shadow.enums.ResultCode;

public class BizException extends RuntimeException {

    private static final long serialVersionUID = -4966587587799868847L;

    private final IResultCode resultCode;

    public BizException(String message) {
        super(message);
        this.resultCode = ResultCode.FAILURE;
    }

    public BizException(IResultCode resultCode) {
        super(resultCode.getMessage());
        this.resultCode = resultCode;
    }

    public BizException(IResultCode resultCode, Throwable cause) {
        super(cause);
        this.resultCode = resultCode;
    }

    public IResultCode getResultCode() {
        return resultCode;
    }

}
