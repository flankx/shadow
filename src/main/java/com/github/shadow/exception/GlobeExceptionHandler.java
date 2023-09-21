package com.github.shadow.exception;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.github.shadow.enums.ResultCode;
import com.github.shadow.pojo.R;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@RestControllerAdvice
public class GlobeExceptionHandler {

    @ExceptionHandler(value = {BizException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public R handle(BizException e) {
        log.error("业务异常！", e);
        return R.fail(e.getResultCode(), e.getMessage());
    }

    @ExceptionHandler(value = {Throwable.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public R handle(Throwable e) {
        log.error("服务器异常！", e);
        return R.fail(ResultCode.INTERNAL_SERVER_ERROR,
            StringUtils.isBlank(e.getMessage()) ? ResultCode.INTERNAL_SERVER_ERROR.getMessage() : e.getMessage());
    }

}
