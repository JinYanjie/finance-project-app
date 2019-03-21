package com.kangce.finance.http.exceptition

import com.google.gson.JsonParseException
import com.google.gson.stream.MalformedJsonException
import org.json.JSONException
import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.text.ParseException

object ExceptionEngine {

    val UN_KNOWN_ERROR = -10000//未知错误
    val ANALYTIC_SERVER_DATA_ERROR = -10001//解析(服务器)数据错误
    val ANALYTIC_CLIENT_DATA_ERROR = -10002//解析(客户端)数据错误
    val CONNECT_ERROR = -10003//网络连接错误
    val TIME_OUT_ERROR = -10004//网络连接超时

    fun handleException(e: Throwable): ApiException {
        val ex: ApiException
        if (e is HttpException) {             //HTTP错误
            ex = ApiException(e, e.code())
            ex.msg="网络不给力，请稍后再试" //均视为网络错误
            return ex
        } else if (e is ServerException) {    //服务器返回的错误
            ex = ApiException(e, e.code)
            ex.msg= e.msg
            return ex
        } else if (e is JsonParseException
                || e is JSONException
                || e is ParseException || e is MalformedJsonException) {  //解析数据错误
            ex = ApiException(e, ANALYTIC_SERVER_DATA_ERROR)
            ex.msg="数据解析失败"
            return ex
        } else if (e is ConnectException) {//连接网络错误
            ex = ApiException(e, CONNECT_ERROR)
            ex.msg="连接服务器失败，请检查您的网络"
            return ex
        } else if (e is SocketTimeoutException) {//网络超时
            ex = ApiException(e, TIME_OUT_ERROR)
            ex.msg="连接服务器超时，请检查您的网络"
            return ex
        } else {  //未知错误
            ex = ApiException(e, UN_KNOWN_ERROR)
            ex.msg="网络不给力，请重试"
            return ex
        }
    }
}