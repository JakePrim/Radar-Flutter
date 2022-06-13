package com.fbe.http.transformer

import java.io.InputStream
import java.lang.reflect.Type

interface ResponseTransformer {
    fun transformer(original: InputStream): InputStream

    interface Factory {
        fun create(type: Type): ResponseTransformer?
    }
}