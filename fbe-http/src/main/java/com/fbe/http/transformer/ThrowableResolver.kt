package com.fbe.http.transformer

import java.lang.reflect.Type

interface ThrowableResolver<T> {
    /**
     * 将错误信息转换成正常的Response
     */
    fun resolve(throwable: Throwable): T

    interface Factory<T> {
        fun create(type: Type): ThrowableResolver<T>?
    }
}