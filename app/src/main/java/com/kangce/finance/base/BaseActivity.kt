package com.kangce.finance.base

import android.os.Bundle
import com.hjq.toast.ToastUtils
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity

import org.greenrobot.eventbus.EventBus

open abstract class BaseActivity : RxAppCompatActivity(), IBaseView {
    private var loadDialog: QMUITipDialog? = null
    protected abstract fun getLayoutId(): Int

    protected open fun init(savedInstanceState: Bundle?) {}

    protected open fun initView() {}

    protected open fun initData() {}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (getLayoutId() != 0) {
            setContentView(getLayoutId())
        }
        // 通过注解绑定控件
        AppManager.instance.addActivity(this)
//        EventBus.getDefault().register(this)

        init(savedInstanceState)
        initView()
        initData()
    }

    override fun onDestroy() {
        if (loadDialog != null && loadDialog?.isShowing!!) {
            loadDialog!!.dismiss()
        }
        super.onDestroy()
        AppManager.instance.finishActivity(this)
//        EventBus.getDefault().unregister(this)

    }

    override fun showLoading() {
        getLoading()?.show()
    }

    override fun closeLoading() {
        getLoading()?.dismiss()
    }

    override fun showToast(msg: String) {
        ToastUtils.show(msg)
    }

    fun getLoading(): QMUITipDialog? {
        if (loadDialog == null) {
            loadDialog = QMUITipDialog.Builder(this)
                    .setIconType(QMUITipDialog.Builder.ICON_TYPE_LOADING)
                    .create()
        }
        return loadDialog
    }



}