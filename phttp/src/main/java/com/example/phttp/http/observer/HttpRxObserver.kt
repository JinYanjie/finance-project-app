package com.example.phttp.http.observer

import com.example.phttp.http.event.TokenExpiredEvent
import com.example.phttp.http.exceptition.ApiException
import com.example.phttp.http.exceptition.ExceptionEngine
import com.kangce.finance.http.service.ApiCodeConfig
import org.greenrobot.eventbus.EventBus


abstract class HttpRxObserver<T> : HttpObserver<T> {

    constructor() {}

    constructor(tag: String) : super(tag) {}

    override fun onError(e: Throwable) {
        RxActionManagerImpl.instance!!.remove(mTag)
        if (e is ApiException) {
            val exception = e as ApiException
            if (exception.code == ApiCodeConfig.TOKEN_EXPIRED|| exception.code == ApiCodeConfig.TOKEN_RELOGIN) {
                // 发送登录过期事件
                EventBus.getDefault().post(TokenExpiredEvent())
            }
            onError(e)
        } else {
            onError(ApiException(e, ExceptionEngine.UN_KNOWN_ERROR))
        }
    }


}
