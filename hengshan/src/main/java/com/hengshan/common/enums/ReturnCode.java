package com.hengshan.common.enums;

public enum ReturnCode {
    SUCCESS(200,"成功"),
    ACCESS_DENIED(401,"非法访问"),
    NO_OPERATOR_AUTH(403,"无访问权限"),
    REQUEST_ERROR(400,"请求错误"),
    SYSTEM_ERROR(500,"服务异常"),

    INVALID_TOKEN(1001,"访问令牌不合法"),
    LOGIN_ERROR(1002,"用户名或密码错误"),
    USERNAME_EXIST(1003,"用户名已存在"),
    PHONENUMBER_EXIST(1004,"手机号已存在"),
    EMAIL_EXIST(1005, "邮箱已存在"),
    REQUIRE_USERNAME(2001, "必需填写用户名"),
    CONTENT_NOT_NULL(2002, "评论内容不能为空"),
    FILE_TYPE_ERROR(2003, "文件类型错误，请上传png文件"),
    USERNAME_NOT_NULL(2004, "用户名不能为空"),
    PASSWORD_NOT_NULL(2006, "密码不能为空"),
    EMAIL_NOT_NULL(2007, "邮箱不能为空");

    private final int code;
    private final String message;

    ReturnCode(int code, String message){
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}