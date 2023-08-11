package com.hengshan.common;

import com.hengshan.common.enums.ReturnCode;

public class ResultBody {

    private int code;
    private String message;
    private Object data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
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

    public ResultBody(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public static ResultBody success(Object data) {
        ResultBody body = new ResultBody();
        body.setCode(ReturnCode.RC200.getCode());
        body.setMessage(ReturnCode.RC200.getMessage());
        body.setData(data);
        return body;
    }

    public static ResultBody fail(int code, String message) {
        ResultBody body = new ResultBody(code,message);
        return body;
    }

    public static ResultBody fail(ReturnCode returnCode) {
        ResultBody body = new ResultBody();
        body.setCode(returnCode.getCode());
        body.setMessage(returnCode.getMessage());
        return body;
    }
}
