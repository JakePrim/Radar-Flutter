package com.edu.common;

/**
 * 响应码信息
 */
public enum StateCode {
    SUCCESS("200", "响应成功"),
    LOGIN_ERROR("400","用户名或密码错误");

    private final String code;
    private final String msg;

    StateCode(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
