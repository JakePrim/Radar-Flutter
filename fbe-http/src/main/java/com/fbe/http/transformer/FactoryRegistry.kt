package com.fbe.http.transformer

import java.lang.reflect.Type

object FactoryRegistry {
    private val responseTransformers = mutableListOf<ResponseTransformer.Factory>()
    private val throwableResolvers = mutableListOf<ThrowableResolver.Factory<*>>()

    @JvmStatic
    fun registerResponseTransformer(factory: ResponseTransformer.Factory) {
        responseTransformers.add(factory)
    }

    @JvmStatic
    fun getResponseTransformer(rawType: Type): ResponseTransformer? {
        responseTransformers.forEach {
            val transformer = it.create(rawType)
            if (transformer != null) {
                return transformer
            }
        }
        return null
    }

    @JvmStatic
    fun registerThrowableResolver(factory: ThrowableResolver.Factory<*>) {
        throwableResolvers.add(factory)
    }

    @JvmStatic
    fun getThrowableResolver(type: Type): ThrowableResolver<*>? {
        throwableResolvers.forEach {
            val resolver = it.create(type)
            if (resolver != null) {
                return resolver
            }
        }
        return null
    }
}