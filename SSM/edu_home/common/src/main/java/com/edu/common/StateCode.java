package com.edu.common;

/**
 * 响应码信息
 */
public enum StateCode {
    SUCCESS("200", "响应成功");

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
