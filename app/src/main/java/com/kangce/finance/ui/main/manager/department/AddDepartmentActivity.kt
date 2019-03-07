package com.kangce.finance.ui.main.manager.department

import android.content.Context
import android.content.Intent
import android.text.Editable
import android.text.TextUtils
import android.view.View
import com.hjq.toast.ToastUtils
import com.kangce.finance.base.BaseActivity
import com.kangce.finance.choumou.R
import com.kangce.finance.choumou.http.exceptition.ApiException
import com.kangce.finance.choumou.http.observer.HttpRxObservable
import com.kangce.finance.choumou.http.observer.HttpRxObserver
import com.kangce.finance.choumou.http.ohkttp.RetrofitManager
import com.kangce.finance.choumou.http.service.ApiService
import com.kangce.finance.utils.L
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_add_department.*
import kotlinx.android.synthetic.main.title.*

class AddDepartmentActivity : BaseActivity() {
    override fun getLayoutId(): Int {
        return R.layout.activity_add_department
    }

    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, AddDepartmentActivity::class.java))
        }
    }

    override fun initView() {
        super.initView()
        tvTitle.text = "添加部门"
        imgRight.visibility = View.VISIBLE
        imgRight.setImageResource(R.drawable.finish)
        back.setOnClickListener { finish() }

        imgRight.setOnClickListener {
            if (TextUtils.isEmpty(dName.text)) {
                ToastUtils.show("请填写部门名称")
            } else {
                addDepartment(dName.text.toString())
            }
        }
    }

    private fun addDepartment(name: String) {
        var departments = RetrofitManager.retrofitManager.getRetrofit(this)
                .create(ApiService::class.java).addDepartment(name)
        HttpRxObservable.getObservable(departments, this).subscribe(object : HttpRxObserver<Any>() {
            override fun onStart(d: Disposable) {
                L.d("start")
            }

            override fun onError(e: ApiException) {
                ToastUtils.show(e.msg)
            }

            override fun onSuccess(response: Any) {
                ToastUtils.show("$name  添加成功")
                dName.setText("")
            }

        })
    }
}