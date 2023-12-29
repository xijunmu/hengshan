package com.hengshan.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.hengshan.common.enums.ReturnCode;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResultBody {

    private Integer code;
    private String message;
    private Object data;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public ResultBody() {

    }

    public ResultBody(Integer code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public ResultBody(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public ResultBody(Integer code, Object data) {
        this.code = code;
        this.data = data;
    }

    public static ResultBody success() {
        ResultBody body = new ResultBody(ReturnCode.SUCCESS.getCode(), ReturnCode.SUCCESS.getMessage());
        return body;
    }

    public static ResultBody success(Object data) {
        ResultBody body = new ResultBody(ReturnCode.SUCCESS.getCode(), ReturnCode.SUCCESS.getMessage());
        if (data != null) {
            body.setData(data);
        }
        return body;
    }

    public static ResultBody fail(int code, String message) {
        ResultBody body = new ResultBody(code, message);
        return body;
    }

    public static ResultBody fail(ReturnCode returnCode) {
        ResultBody body = new ResultBody(returnCode.getCode(), returnCode.getMessage());
        return body;
    }

    public static ResultBody fail(ReturnCode returnCode, String message) {
        ResultBody body = new ResultBody(returnCode.getCode(), message);
        return body;
    }
}
