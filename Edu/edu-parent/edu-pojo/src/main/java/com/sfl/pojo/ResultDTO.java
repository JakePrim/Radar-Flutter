package com.sfl.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * @program: Edu
 * @Description: 接口响应格式
 * @author: sufulu
 * @version: 1.0.0
 * @create: 2021-02-05 14:58
 * @PackageName: com.sfl.pojo
 * @ClassName: ResultDTO.java
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ResultDTO<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer state; //操作状态
    private String message;// 状态描述
    private T content;// 响应内容

    public static <T> ResultDTO<T> createSuccess(String message) {
        return new ResultDTO<>(200, message, null);
    }

    public static <T> ResultDTO<T> createSuccess(String message, T content) {
        return new ResultDTO<>(200, message, content);
    }

    public static <T> ResultDTO<T> createError(String message, T content) {
        return new ResultDTO<>(400, message, content);
    }

    public static <T> ResultDTO<T> createError(String message) {
        return new ResultDTO<>(400, message, null);
    }

    public static <T> ResultDTO<T> create(Integer state, String message, T content) {
        return new ResultDTO<>(state, message, content);
    }
}
