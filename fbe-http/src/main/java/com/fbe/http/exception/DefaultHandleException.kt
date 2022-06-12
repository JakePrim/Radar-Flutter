package com.fbe.http.exception

class DefaultHandleException:IHandleException {
    override fun handlerException(exception: IException, t: Throwable) {
        val responseThrowable = exception.defineException(t)

    }
}