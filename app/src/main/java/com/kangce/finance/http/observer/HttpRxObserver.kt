package com.kangce.finance.choumou.http.observer

import com.kangce.finance.choumou.http.exceptition.ApiException
import com.kangce.finance.choumou.http.exceptition.ExceptionEngine
import com.kangce.finance.choumou.http.service.ApiCodeConfig
import com.kangce.finance.event.TokenExpiredEvent
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
