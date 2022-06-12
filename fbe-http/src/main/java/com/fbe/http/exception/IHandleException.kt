package com.fbe.http.exception

import java.lang.Exception

/**
 * handle exception,global exception handle
 */
interface IHandleException {
    fun handlerException(exception: IException, t: Throwable)
}