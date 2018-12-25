package com.example.jyj.choumou.http.observer

import com.example.jyj.choumou.http.exceptition.ExceptionEngine
import com.example.jyj.choumou.utils.L
import io.reactivex.Observable
import io.reactivex.annotations.NonNull
import io.reactivex.functions.Function

class HttpResultFunction<T> : Function<Throwable, Observable<T>> {
    @Throws(Exception::class)
    override fun apply(@NonNull throwable: Throwable): Observable<T> {
        //打印具体错误
        L.e("HttpResultFunction:$throwable")
        return Observable.error(ExceptionEngine.handleException(throwable))
    }
}
