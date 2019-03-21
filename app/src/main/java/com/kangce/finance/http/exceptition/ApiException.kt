package com.kangce.finance.http.exceptition

class ApiException(throwable:Throwable,code: Int):RuntimeException(throwable) {
    var code: Int = 0//错误码
    var msg: String=""//错误信息
    init {
        this.code=code
    }
}