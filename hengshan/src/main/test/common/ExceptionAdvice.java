package com.hengshan.common;

import com.hengshan.common.enums.ReturnCode;
import com.hengshan.exception.LoginException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionAdvice {

    private final Logger logger = LogManager.getLogger(ExceptionAdvice.class);

    //全局异常处理
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResultBody exception(Exception e) {
        logger.error("ex={}", e.getMessage(),e);
        return ResultBody.fail(ReturnCode.RC500.getCode(),e.getClass()+":"+e.getMessage());
    }

    //请求参数异常处理
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResultBody httpMessageException(HttpMessageNotReadableException e) {
        logger.error("ex={}", e.getMessage(),e);
        return ResultBody.fail(ReturnCode.RC400);
    }

    //自定义异常处理
    @ExceptionHandler(LoginException.class)
    public ResultBody loginException(LoginException e) {
        logger.error("ex={}", e.getMessage(),e);
        return ResultBody.fail(ReturnCode.USERNAME_OR_PASSWORD_ERROR.getCode(),e.getMessage());
    }

}
