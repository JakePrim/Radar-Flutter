package com.jakeprim.base;

import com.alibaba.fastjson.JSONObject;

public enum StatusCode {
    SUCCESS(0, "success"), FAIL(1, "fail");
    private int code;
    private String message;

    StatusCode() {
    }

    StatusCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

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

    @Override
    public String toString() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("status", code);
        jsonObject.put("msg", message);
        return jsonObject.toJSONString();
    }
}
