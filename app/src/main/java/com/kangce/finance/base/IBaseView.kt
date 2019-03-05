package com.kangce.finance.base

interface IBaseView {
    abstract fun showLoading()
    abstract fun closeLoading()
    abstract fun showToast(msg: String)
}