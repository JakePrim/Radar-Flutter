package com.fbe.http.exception

/**
 * User-defined exception information
 */
interface IException {
    /**
     * define Exception return ResponseThrowable
     */
    fun defineException(throwable: Throwable): ResponseThrowable

    /**
     * business success code,General is 200/20000 or your business other code
     */
    fun businessCode(): Int
}