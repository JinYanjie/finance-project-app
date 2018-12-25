package com.example.jyj.choumou.http.observer

import com.example.jyj.choumou.bean.DataBean
import com.trello.rxlifecycle2.LifecycleProvider
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

object HttpRxObservable {
    /**
     * 获取被监听者
     *
     * @param apiObservable
     * @param lifecycle     传入LifecycleProvider自动管理生命周期,避免内存溢出
     * @return
     */
    fun <T> getObservable(apiObservable: Observable<DataBean<T>>, lifecycle: LifecycleProvider<*>?): Observable<T> {
        val observable: Observable<T>
        if (lifecycle != null) {
            //随生命周期自动管理.eg:onCreate(start)->onStop(end)
            observable = apiObservable
                    .map<T>(ServerResultFunction<T>())
                    .compose(lifecycle.bindToLifecycle())//需要在这个位置添加
                    .onErrorResumeNext(HttpResultFunction<T>())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
        } else {
            observable = apiObservable
                    .map<T>(ServerResultFunction<T>())
                    .onErrorResumeNext(HttpResultFunction<T>())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
        }
        return observable
    }
}