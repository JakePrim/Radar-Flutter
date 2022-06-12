package com.fbe.http.exception

import android.content.Context
import android.net.ParseException
import com.abstergo.http.R
import com.google.gson.JsonParseException
import com.google.gson.JsonSyntaxException
import com.google.gson.stream.MalformedJsonException
import org.apache.http.conn.ConnectTimeoutException
import org.json.JSONException
import retrofit2.HttpException
import java.lang.IllegalStateException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.net.ssl.SSLException

class DefaultDefineException(private val context: Context) : IException {
    override fun defineException(e: Throwable): ResponseThrowable {
        val ex: ResponseThrowable
        return if (e is HttpException) {
            ex = ResponseThrowable(e, SYSTEM_ERROR.HTTP_ERROR)
            when (e.code()) {
                SYSTEM_ERROR.UNAUTHORIZED -> {
                    ex.setMessage(context.getString(R.string.unauthorized))

                    ex.code = SYSTEM_ERROR.UNAUTHORIZED
                }
                SYSTEM_ERROR.FORBIDDEN -> ex.setMessage(context.getString(R.string.forbidden_error))

                SYSTEM_ERROR.NOT_FOUND -> ex.setMessage(context.getString(R.string.res_not_found))

                SYSTEM_ERROR.REQUEST_TIMEOUT -> ex.setMessage(context.getString(R.string.request_timeout))

                SYSTEM_ERROR.INTERNAL_SERVER_ERROR -> ex.setMessage(context.getString(R.string.internal_server_error))

                SYSTEM_ERROR.SERVICE_UNAVAILABLE -> ex.setMessage(context.getString(R.string.service_unavailable))

                else -> ex.setMessage(context.getString(R.string.net_work_error))
            }
            ex
        } else if (e is JsonParseException || e is JsonSyntaxException || e is IllegalStateException || e is JSONException || e is ParseException || e is MalformedJsonException) {
            ex = ResponseThrowable(e, SYSTEM_ERROR.PARSE_ERROR)
            ex.setMessage(context.getString(R.string.parse_error))
            ex
        } else if (e is ConnectException) {
            ex = ResponseThrowable(e, SYSTEM_ERROR.NETWORD_ERROR)
            ex.setMessage(context.getString(R.string.net_work_error))
            ex
        } else if (e is SSLException) {
            ex = ResponseThrowable(e, SYSTEM_ERROR.SSL_ERROR)
            ex.setMessage(context.getString(R.string.ssl_error))
            ex
        } else if (e is ConnectTimeoutException) {
            ex = ResponseThrowable(e, SYSTEM_ERROR.TIMEOUT_ERROR)
            ex.setMessage(context.getString(R.string.connect_timeout_error))
            ex
        } else if (e is SocketTimeoutException) {
            ex = ResponseThrowable(e, SYSTEM_ERROR.TIMEOUT_ERROR)
            ex.setMessage(context.getString(R.string.connect_timeout_error))
            ex
        } else if (e is UnknownHostException) {
            ex = ResponseThrowable(e, SYSTEM_ERROR.TIMEOUT_ERROR)
            ex.setMessage(context.getString(R.string.unknown_host))
            ex
        } else if (e is ResponseThrowable) {//业务异常
            ex = ResponseThrowable(e, e.code)
            e.message?.let { ex.setMessage(it) }
            ex
        } else {
            ex = ResponseThrowable(e, SYSTEM_ERROR.NETWORD_ERROR)
            ex.setMessage(context.getString(R.string.net_work_error))
            ex
        }
    }

    override fun businessCode(): Int {
        return APP_ERROR.SUCC
    }

    interface SYSTEM_ERROR {
        companion object {
            const val UNAUTHORIZED = 401
            const val FORBIDDEN = 403
            const val NOT_FOUND = 404
            const val REQUEST_TIMEOUT = 408
            const val INTERNAL_SERVER_ERROR = 500
            const val SERVICE_UNAVAILABLE = 503

            /**
             * 未知错误
             */
            const val UNKNOWN = 1000

            /**
             * 解析错误
             */
            const val PARSE_ERROR = 1001

            /**
             * SSL_ERROR         * 网络错误
             */
            const val NETWORD_ERROR = 1002

            /**
             * 协议出错
             */
            const val HTTP_ERROR = 1003

            /**
             * 证书出错
             */
            const val SSL_ERROR = 1005

            /**
             * 连接超时
             */
            const val TIMEOUT_ERROR = 1006
        }
    }

    interface APP_ERROR {
        companion object {
            const val SUCC = 0 //	处理成功，无错误
            const val UNAUTHORIZED = 60000 //token失效
            const val PHONEVERIFYERROR = 50000 //手机号或验证码错误

        }
    }
}