package com.kangce.finance.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.trello.rxlifecycle2.components.support.RxFragment

abstract class BaseFragment : RxFragment() {
    protected abstract fun getLayoutId(): Int
    protected open fun initView(view: View) {}
    protected open fun initData() {}
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val mRootView = inflater.inflate(getLayoutId(), container, false)
        initView(mRootView)
        initData()
        return mRootView
    }
}