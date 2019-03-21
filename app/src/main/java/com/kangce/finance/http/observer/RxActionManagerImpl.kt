package com.kangce.finance.http.observer

import android.support.v4.util.ArrayMap
import io.reactivex.disposables.Disposable

class RxActionManagerImpl
private constructor() : RxActionManager<Any> {
    private val mMaps: ArrayMap<Any, Disposable> = ArrayMap()//处理,请求列表

    override fun add(tag: Any, disposable: Disposable) {
        mMaps[tag] = disposable
    }


    override fun remove(tag: Any) {
        if (!mMaps.isEmpty) {
            mMaps.remove(tag)
        }
    }


    override fun cancel(tag: Any) {
        if (mMaps.isEmpty) {
            return
        }
        if (mMaps[tag] == null) {
            return
        }
        if (!mMaps[tag]!!.isDisposed) {
            mMaps[tag]!!.dispose()
        }
        mMaps.remove(tag)
    }

    /**
     * 判断是否取消了请求
     *
     * @param tag
     * @return
     */
    fun isDisposed(tag: Any): Boolean {
        return if (mMaps.isEmpty || mMaps[tag] == null) true else mMaps[tag]!!.isDisposed
    }

    companion object {

        @Volatile
        private var mInstance: RxActionManagerImpl? = null
        val instance: RxActionManagerImpl?
            get() {
                if (mInstance == null) {
                    synchronized(RxActionManagerImpl::class.java) {
                        if (mInstance == null) {
                            mInstance = RxActionManagerImpl()
                        }
                    }
                }
                return mInstance
            }
    }
}
