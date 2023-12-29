package com.hengshan.exception;

import com.hengshan.common.enums.ReturnCode;

public class SystemException extends RuntimeException {

    private int code;

    private String message;

    public int getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public SystemException(ReturnCode returnCode) {
        this.code = returnCode.getCode();
        this.message = returnCode.getMessage();
    }

}
