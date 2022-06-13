package com.fbe.http.exception

import java.lang.Exception


object RegistryException {

    private var defineException: IException? = null

    private var handleException: IHandleException? = null

    @JvmStatic
    fun registerDefineException(defineException: IException) {
        this.defineException = defineException
    }

    @JvmStatic
    fun registerHandleException(handleException: IHandleException) {
        this.handleException = handleException
    }

    @JvmStatic
    fun getDefineException(): IException? {
        return defineException
    }

    @JvmStatic
    fun getHandleException(): IHandleException? {
        return handleException
    }
}