package com.fbe.http.config

import android.content.Context
import com.fbe.http.exception.IException
import com.fbe.http.exception.IHandleException
import com.fbe.http.transformer.ThrowableResolver
import com.fbe.http.transformer.ResponseTransformer
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit

interface IConfig {

    fun isDebug(debug: Boolean): IConfig

    fun baseUrl(baseUrl: String): IConfig

    /**
     * 添加拦截器
     */
    fun addInterceptor(interceptor: Interceptor): IConfig

    /**
     * 定义系统异常
     */
    fun setDefineException(exception: IException): IConfig

    /**
     * 设置全局系统异常处理
     */
    fun setHandleException(handle: IHandleException): IConfig

    /**
     * 自己定义OkHttpBuilder,会替换框架内部的OkHttpBuilder
     */
    fun setOkhttpConfig(okhttpBuilder: OkHttpClient.Builder): IConfig

    /**
     * 在当前框架的基础上添加OKhttpBuilder的配置
     */
    fun addOkHttpBuilderSetting(block: (OkHttpClient.Builder) -> Unit): IConfig

    /**
     * 在当前框架的基础上添加RetrofitBuilder配置
     */
    fun addRetrofitBuilderSetting(block: (Retrofit.Builder) -> Unit): IConfig

    /**
     * 注册异常转换器，将系统异常信息转换为 response
     */
    fun registerResponseTransformer(factory: ResponseTransformer.Factory): IConfig

    /**
     * 注册response转换器，转换成BaseResponse一一对应
     */
    fun registerThrowableResolver(factory: ThrowableResolver.Factory<*>): IConfig

    fun build(context: Context)
}