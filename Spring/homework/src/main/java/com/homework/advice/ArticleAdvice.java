package com.homework.advice;

import org.springframework.stereotype.Component;

/**
 * 通知类
 */
@Component("articleAdvice")
public class ArticleAdvice {

    /**
     * 前置增强方法
     */
    public void before() {
        System.out.println("前置增强...");
    }
}
