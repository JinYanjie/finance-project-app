package com.kangce.finance.http.observer

import android.text.TextUtils
import com.kangce.finance.http.exceptition.ApiException
import com.kangce.finance.http.exceptition.ExceptionEngine
import io.reactivex.Observer
import io.reactivex.annotations.NonNull
import io.reactivex.disposables.Disposable

abstract class HttpObserver<T> : Observer<T>, HttpRequestListener {

    protected var mTag: String=""//请求标识

    /**
     * 是否已经处理
     *
     * @author ZhongDaFeng
     */
    val isDisposed: Boolean
        get() = if (TextUtils.isEmpty(mTag)) {
            true
        } else RxActionManagerImpl.instance!!.isDisposed(mTag)

    constructor() {}

    constructor(tag: String) {
        this.mTag = tag
    }

    override fun onError(e: Throwable) {
        RxActionManagerImpl.instance!!.remove(mTag)
        if (e is ApiException) {
            onError(e as ApiException)
        } else {
            onError(ApiException(e, ExceptionEngine.UN_KNOWN_ERROR))
        }
    }

    override fun onComplete() {}

    override fun onNext(@NonNull t: T) {
        if (!TextUtils.isEmpty(mTag)) {
            RxActionManagerImpl.instance!!.remove(mTag)
        }
        onSuccess(t)
    }

    override fun onSubscribe(@NonNull d: Disposable) {
        if (!TextUtils.isEmpty(mTag)) {
            RxActionManagerImpl.instance!!.add(mTag, d)
        }
        onStart(d)
    }

    override fun cancel() {
        if (!TextUtils.isEmpty(mTag)) {
            RxActionManagerImpl.instance!!.cancel(mTag)
        }
    }

    protected abstract fun onStart(d: Disposable)

    /**
     * 错误/异常回调
     *
     * @author ZhongDaFeng
     */
    protected abstract fun onError(e: ApiException)

    /**
     * 成功回调
     *
     * @author ZhongDaFeng
     */
    protected abstract fun onSuccess(response: T)
}