package com.fbe.http

import com.fbe.http.config.HttpConfig
import com.fbe.http.config.IConfig
import com.fbe.http.exception.RegistryException
import com.fbe.http.transformer.FactoryRegistry
import com.fbe.http.transformer.ThrowableResolver
import java.lang.reflect.*
import kotlin.coroutines.Continuation
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.intrinsics.intercepted

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
        httpConfig.registerThrowableResolver(BaseAPIThrowableResolverFactory())
        return httpConfig
    }

    inline fun <reified T> create(): T {
        return httpConfig.retrofit.create(T::class.java).proxyRetrofit()
    }

    inline fun <reified T> T.proxyRetrofit(): T {
        // 获取原先的 retrofit 的代理对象的的 InvocationHandler
        // 这样我就可以继续使用 retrofit 的能力进行网络请求
        val retrofitHandler = Proxy.getInvocationHandler(this)
        return Proxy.newProxyInstance(
            T::class.java.classLoader, arrayOf(T::class.java)
        ) { proxy, method, args ->
            // 判断当前是为 suspend 方法
            method.takeIf { it.isSuspendMethod }?.getSuspendReturnType()
                ?.let { FactoryRegistry.getThrowableResolver(it) }
                ?.let { resolver ->
                    // 替换原始的 Contiuation 对象，这样我们就可以对异常进行拦截
                    args.updateAt(
                        args.lastIndex,
                        FakeSuccessContinuationWrapper(
                            //intercepted保证协程上下文的调度一致，否则会新启一个线程
                            (args.last() as Continuation<Any>).intercepted(),
                            resolver as ThrowableResolver<Any>
                        )
                    )
                }
            retrofitHandler.invoke(proxy, method, args)
        } as T
    }

    /**
     * 基于BaseAPIResult的返回类型，异常处理，
     * 如果自定义了APIResult 需要注册FactoryRegistry.registryThrowableResolver,注册返回对应类型的异常处理
     */
    class BaseAPIThrowableResolverFactory : ThrowableResolver.Factory<BaseAPIResult<*>> {
        override fun create(type: Type): ThrowableResolver<BaseAPIResult<*>>? {
            return (type as? ParameterizedType)?.rawType
                ?.takeIf { it == BaseAPIResult::class.java }
                ?.let { Resolver() }
        }

        inner class Resolver : ThrowableResolver<BaseAPIResult<*>> {
            override fun resolve(throwable: Throwable): BaseAPIResult<*> {
                var code = -1
                var message = "网络异常"
                RegistryException.getDefineException()?.let {
                    val responseThrowable = it.defineException(throwable)
                    code = responseThrowable.code
                    message = responseThrowable.message ?: "网络异常"
                    //全局的异常提示
                    RegistryException.getHandleException()?.handlerException(it, throwable)
                }
                //将异常转换为BaseAPIResult
                return BaseAPIResult<Any>(
                    code,
                    null,
                    message,
                )
            }
        }

    }

    /**
     * 将系统异常转换为请求成功的状态
     */
    class FakeSuccessContinuationWrapper<T>(
        private val original: Continuation<T>,
        private val resolver: ThrowableResolver<T>
    ) :
        Continuation<T> {
        override val context: CoroutineContext = original.context

        override fun resumeWith(result: Result<T>) {
            result.onSuccess {
                // when it's success, resume with original Continuation
                original.resumeWith(result)
            }.onFailure {
                // when it's failure, resume a wrapper success which contain
                // failure, so we don't need to add try catch
                val fakeSuccessResult = resolver.resolve(it)
                original.resumeWith(Result.success(fakeSuccessResult))
            }
        }

    }

    /**
     * A property to indicate where the method is a suspend method or not.
     */
    val Method.isSuspendMethod: Boolean
        get() = genericParameterTypes.lastOrNull()
            ?.let { it as? ParameterizedType }?.rawType == Continuation::class.java

    /**
     * Get a suspend method return type, if the method is not a suspend method return null.
     *
     * @return return type of the suspend method.
     */
    fun Method.getSuspendReturnType(): Type? {
        return genericParameterTypes.lastOrNull()
            ?.let { it as? ParameterizedType }?.actualTypeArguments?.firstOrNull()
            ?.let { it as? WildcardType }?.lowerBounds?.firstOrNull()
    }

    /**
     * Update Array at a special index.
     *
     * @param index the index to update
     * @param updated the updated value
     */
    fun Array<Any?>.updateAt(index: Int, updated: Any?) {
        this[index] = updated
    }

}