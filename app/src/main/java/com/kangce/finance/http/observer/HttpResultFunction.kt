package com.kangce.finance.choumou.http.observer

import com.kangce.finance.choumou.http.exceptition.ExceptionEngine
import com.kangce.finance.utils.L
import io.reactivex.Observable
import io.reactivex.annotations.NonNull
import io.reactivex.functions.Function

class HttpResultFunction<T> : Function<Throwable, Observable<T>> {
    @Throws(Exception::class)
    override fun apply(@NonNull throwable: Throwable): Observable<T> {
        //打印具体错误
//        L.e("HttpResultFunction:$throwable")
        return Observable.error(ExceptionEngine.handleException(throwable))
    }
}
