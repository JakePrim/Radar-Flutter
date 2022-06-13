package com.fbe.http.transformer

import okhttp3.ResponseBody
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import java.lang.reflect.Type

class TransformConverterFactory:Converter.Factory() {
    override fun responseBodyConverter(
        type: Type,
        annotations: Array<out Annotation>,
        retrofit: Retrofit
    ): Converter<ResponseBody, *>? {
        return FactoryRegistry.getResponseTransformer(type)?.let { transformer ->
            TransformConverter(
                retrofit.nextResponseBodyConverter(this, type, annotations),
                transformer
            )
        }
    }

    inner class TransformConverter(
        private val baseConverter: Converter<ResponseBody, Any>,
        private val responseTransformer: ResponseTransformer
    ) : Converter<ResponseBody, Any> {
        override fun convert(value: ResponseBody): Any? {
            val transformed = responseTransformer.transformer(value.byteStream()).readBytes()
                .toResponseBody(value.contentType())
            return baseConverter.convert(transformed)
        }
    }
}