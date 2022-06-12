package com.fbe.http.config

import android.content.Context
import com.fbe.http.exception.IException
import com.fbe.http.exception.IHandleException
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import java.lang.Exception

interface IConfig {

    fun isDebug(debug: Boolean): IConfig

    fun baseUrl(baseUrl: String): IConfig

    fun addInterceptor(interceptor: Interceptor): IConfig

    fun setDefineException(exception: IException): IConfig

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


    fun build(context: Context)
}