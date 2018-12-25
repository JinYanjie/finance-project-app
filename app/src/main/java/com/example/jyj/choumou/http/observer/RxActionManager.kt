package com.example.jyj.choumou.http.observer

import io.reactivex.disposables.Disposable

interface RxActionManager<T> {
    /**
     * 添加
     *
     * @param tag
     * @param disposable
     */
    fun add(tag: T, disposable: Disposable)

    /**
     * 移除
     *
     * @param tag
     */
    fun remove(tag: T)

    /**
     * 取消
     *
     * @param tag
     */
    fun cancel(tag: T)
}
