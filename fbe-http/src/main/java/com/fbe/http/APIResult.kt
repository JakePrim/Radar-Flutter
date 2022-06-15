package com.fbe.http

import com.fbe.http.exception.RegistryException
import com.fbe.http.exception.ResponseThrowable

/**
 * 框架内部不会对 BaseResponse进行封装处理，而是交给框架的使用者
 * 框架内部提供转换接口，这样框架可以对任何返回的数据进行处理
 */
interface APIResult<T, F : APIResult.Failure> {
    interface Failure {
        fun getThrowable(): ResponseThrowable
    }

    val isSuccess: Boolean

    val isFailure: Boolean
        get() {
            return isSuccess.not()
        }

    val data: T?

    val failure: F?
}

inline fun <T, F, R : APIResult<T, F>> R.onSuccess(block: (data: T?) -> Unit): R {
    data.takeIf { isSuccess }.let(block)
    return this
}

inline fun <T, F, R : APIResult<T, F>> R.onFailure(block: (failure: F?) -> Unit): R {
    failure.takeIf { isFailure }.let(block)
    return this
}

/**
 * 根据上一个Failure，进行下一个请求
 */
inline fun <IT, IF, IR : APIResult<IT, IF>, OT, OF, OR : APIResult<OT, OF>> IR.onFailureThen(block: (failure: IF) -> OR): OR? {
    return failure?.takeIf { isFailure }?.let(block)
}

/**
 * 根据上一个Success返回的Data,进行下一个请求
 */
inline fun <IT, IF, IR : APIResult<IT, IF>, OT, OF, OR : APIResult<OT, OF>> IR.onSuccessThen(block: (data: IT) -> OR): OR? {
    return data?.takeIf { isSuccess }?.let(block)
}