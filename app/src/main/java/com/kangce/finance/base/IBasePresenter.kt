package com.kangce.finance.base

import java.lang.ref.Reference
import java.lang.ref.WeakReference

abstract class IBasePresenter<V, T> {
    private var mViewRef: Reference<V>? = null
    private var mLifecycleRef: Reference<T>? = null

    constructor(view: V, lifeCycler: T?) {
        attachView(view)
        attachLifecycle(lifeCycler)
    }

    private fun attachView(view: V) {
        mViewRef = WeakReference(view)
    }

    private fun attachLifecycle(lifeCycler: T?) {
        mLifecycleRef = WeakReference(lifeCycler)
    }

    fun getView(): V? {
        return mViewRef?.get()
    }

    fun getLifecycle(): T? {
        return mLifecycleRef?.get()
    }
}