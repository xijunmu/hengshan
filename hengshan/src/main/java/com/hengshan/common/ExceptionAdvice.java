package com.hengshan.common;

import com.hengshan.common.enums.ReturnCode;
import com.hengshan.exception.SystemException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局统一异常返回处理
 */
@RestControllerAdvice
@Slf4j
public class ExceptionAdvice {

    //全局异常处理
    @ExceptionHandler(Exception.class)
    public ResultBody exceptionHandler(Exception e) {
        log.error("exception={}", e.getMessage(),e);
        return ResultBody.fail(ReturnCode.SYSTEM_ERROR.getCode(),e.getMessage());
    }

    //自定义SystemException异常处理
    @ExceptionHandler(SystemException.class)
    public ResultBody systemExceptionHandler(SystemException e) {
        log.error("exception={}", e.getMessage(),e);
        return ResultBody.fail(e.getCode(),e.getMessage());
    }

    //spring security认证授权相关异常
    @ExceptionHandler(BadCredentialsException.class)
    public ResultBody badCredentialsExceptionHandler(BadCredentialsException e) {
        log.error("exception={}", e.getMessage(),e);
        return ResultBody.fail(ReturnCode.LOGIN_ERROR.getCode(),e.getMessage());
    }

    //spring security认证授权相关异常
    @ExceptionHandler(InsufficientAuthenticationException.class)
    public ResultBody insufficientAuthenticationExceptionHandler(InsufficientAuthenticationException e) {
        log.error("exception={}", e.getMessage(),e);
        return ResultBody.fail(ReturnCode.INVALID_TOKEN.getCode(),e.getMessage());
    }

    //请求参数异常处理
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResultBody httpMessageException(HttpMessageNotReadableException e) {
        log.error("exception={}", e.getMessage(),e);
        return ResultBody.fail(ReturnCode.REQUEST_ERROR);
    }
}
