package com.fbe.http

import com.fbe.http.config.HttpConfig
import com.fbe.http.config.IConfig

class FbeHttp : IHttp {
    companion object : BaseSingleton<String, FbeHttp>() {
        override val creator: (String?) -> FbeHttp
            get() = {
                FbeHttp()
            }
    }

    lateinit var httpConfig: HttpConfig

    override fun with(): IConfig {
        this.httpConfig = HttpConfig.create()
        return httpConfig
    }

    inline fun <reified T> create(): T {
        return httpConfig.retrofit.create(T::class.java)
    }


}