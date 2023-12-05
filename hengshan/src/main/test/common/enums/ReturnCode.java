package com.hengshan.common.enums;

public enum ReturnCode {
    /**操作成功**/
    RC200(200,"成功"),
    /**授权规则不通过**/
    RC204(204,"授权规则不通过,请稍后再试!"),
    /**access_denied**/
    RC403(403,"无访问权限,请联系管理员授予权限"),
    /**access_denied**/
    RC401(401,"匿名用户访问无权限资源时的异常"),
    /**access_denied**/
    RC400(400,"请求错误"),
    /**服务异常**/
    RC500(500,"服务异常"),

    INVALID_TOKEN(2001,"访问令牌不合法"),
    ACCESS_DENIED(2003,"没有权限访问该资源"),
    TOKEN_AUTHENTICATION_FAILED(1001,"token认证失败"),
    USERNAME_OR_PASSWORD_ERROR(1002,"用户名或密码错误"),
    UNSUPPORTED_GRANT_TYPE(1003, "不支持的认证模式");

    /**自定义状态码**/
    private final int code;
    /**自定义描述**/
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
