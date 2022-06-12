package com.fbe.http.exception

class ResponseThrowable : Exception {
    var code: Int = -1
    private var eMessage: String = ""

    constructor(throwable: Throwable, code: Int) : super(throwable) {
        this.code = code
    }

    constructor(code: Int, message: String) {
        this.code = code
        this.eMessage = message
    }

    override val message: String?
        get() = eMessage.ifEmpty { super.message }

    fun setMessage(message: String) {
        this.eMessage = message
    }
}