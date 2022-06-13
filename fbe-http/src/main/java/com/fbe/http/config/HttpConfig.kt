package com.fbe.http.config

import android.content.Context
import com.fbe.http.exception.*
import com.fbe.http.ssl.SSLContextUtil
import com.fbe.http.transformer.FactoryRegistry
import com.fbe.http.transformer.ResponseTransformer
import com.fbe.http.transformer.ThrowableResolver
import com.fbe.http.transformer.TransformConverterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.reflect.Type
import java.util.concurrent.TimeUnit
import javax.net.ssl.SSLContext

class HttpConfig : IConfig {
    companion object {
        private const val DEFAULT_CONNECT_TIMEOUT = 30
        private const val DEFAULT_WRITE_TIMEOUT = 30
        private const val DEFAULT_READ_TIMEOUT = 30
        fun create(): HttpConfig {
            return HttpConfig()
        }
    }

    lateinit var retrofit: Retrofit

    var okhttpBuilder = OkHttpClient.Builder()

    lateinit var context: Context

    lateinit var baseUrl: String

    lateinit var okHttpClient: OkHttpClient

    var defineException: IException? = null

    var handleException: IHandleException? = null

    val retrofitBuilder = Retrofit.Builder()

    var isDebug = false

    init {
        okhttpBuilder.connectTimeout(DEFAULT_CONNECT_TIMEOUT.toLong(), TimeUnit.SECONDS)
        okhttpBuilder.readTimeout(DEFAULT_WRITE_TIMEOUT.toLong(), TimeUnit.SECONDS)
        okhttpBuilder.writeTimeout(DEFAULT_READ_TIMEOUT.toLong(), TimeUnit.SECONDS)
        //支持https
        val sslContext: SSLContext = SSLContextUtil.getDefaultSLLContext()
        okhttpBuilder.hostnameVerifier(SSLContextUtil.HOSTNAME_VERIFIER)
    }

    override fun isDebug(debug: Boolean): IConfig {
        this.isDebug = debug
        return this
    }

    override fun baseUrl(baseUrl: String): IConfig {
        this.baseUrl = baseUrl
        return this
    }

    override fun addInterceptor(interceptor: Interceptor): IConfig {
        this.okhttpBuilder.addInterceptor(interceptor)
        return this
    }

    override fun setDefineException(exception: IException): IConfig {
        this.defineException = exception
        RegistryException.registerDefineException(exception)
        return this
    }

    override fun setHandleException(handle: IHandleException): IConfig {
        this.handleException = handle
        RegistryException.registerHandleException(handle)
        return this
    }

    override fun setOkhttpConfig(okhttpBuilder: OkHttpClient.Builder): IConfig {
        this.okhttpBuilder = okhttpBuilder
        return this
    }

    override fun addOkHttpBuilderSetting(block: (OkHttpClient.Builder) -> Unit): IConfig {
        block.invoke(this.okhttpBuilder)
        return this
    }

    override fun addRetrofitBuilderSetting(block: (Retrofit.Builder) -> Unit): IConfig {
        block.invoke(this.retrofitBuilder)
        return this
    }

    override fun registerResponseTransformer(factory: ResponseTransformer.Factory): IConfig {
        FactoryRegistry.registerResponseTransformer(factory)
        return this
    }

    override fun registerThrowableResolver(factory: ThrowableResolver.Factory<*>): IConfig {
        FactoryRegistry.registerThrowableResolver(factory)
        return this
    }

    override fun build(context: Context) {
        this.context = context
        if (this.defineException == null) {
            this.defineException = DefaultDefineException(context)
            RegistryException.registerDefineException(this.defineException!!)
        }
        if (this.handleException == null) {
            this.handleException = DefaultHandleException()
            RegistryException.registerHandleException(this.handleException!!)
        }
        this.okHttpClient = okhttpBuilder.build()
        this.retrofit = retrofitBuilder
            .client(okHttpClient)
            .addConverterFactory(NullOnEmptyConverterFactory())
            .addConverterFactory(TransformConverterFactory())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(baseUrl)
            .build()
    }

    class NullOnEmptyConverterFactory : Converter.Factory() {
        override fun responseBodyConverter(
            type: Type,
            annotations: Array<out Annotation>,
            retrofit: Retrofit
        ): Converter<ResponseBody, *> {
            val delegate: Converter<ResponseBody, *> =
                retrofit.nextResponseBodyConverter<Any>(this, type, annotations)
            return Converter<ResponseBody, Any> { body ->
                if (body.contentLength() == 0L) null else delegate.convert(
                    body
                )
            }
        }
    }
}