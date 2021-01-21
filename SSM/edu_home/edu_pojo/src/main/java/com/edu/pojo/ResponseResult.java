package com.edu.pojo;

/**
 * 响应的实体类
 */
public class ResponseResult {
    private Boolean success;
    private String state;
    private String message;
    private Object content;

    public ResponseResult() {
    }

    public ResponseResult(Boolean success, String state, String message, Object content) {
        this.success = success;
        this.state = state;
        this.message = message;
        this.content = content;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getContent() {
        return content;
    }

    public void setContent(Object content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "ResponseResult{" +
                "success=" + success +
                ", state='" + state + '\'' +
                ", message='" + message + '\'' +
                ", content=" + content +
                '}';
    }
}
