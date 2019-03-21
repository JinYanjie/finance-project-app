package com.kangce.finance.ui.manager.department

import android.content.Context
import android.content.Intent
import android.text.TextUtils
import android.view.View
import com.hjq.toast.ToastUtils
import com.kangce.finance.base.BaseActivity
import com.kangce.finance.bean.DepartmentBean
import com.kangce.finance.choumou.R
import com.kangce.finance.choumou.http.exceptition.ApiException
import com.kangce.finance.choumou.http.observer.HttpRxObservable
import com.kangce.finance.choumou.http.observer.HttpRxObserver
import com.kangce.finance.choumou.http.ohkttp.RetrofitManager
import com.kangce.finance.choumou.http.service.ApiService
import com.kangce.finance.widget.DialogMessageHint
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_edit_department.*
import kotlinx.android.synthetic.main.title.*

class DeleteDepartmentActivity : BaseActivity() {
    override fun getLayoutId(): Int {
        return R.layout.activity_edit_department
    }

    companion object {
        fun start(context: Context, department: DepartmentBean) {
            var intent = Intent(context, DeleteDepartmentActivity::class.java)
            intent.putExtra("department", department)
            context.startActivity(intent)
        }
    }

    override fun initView() {
        super.initView()
        var department = intent.getSerializableExtra("department") as DepartmentBean
        etName.setText(department.name)
        tvTitle.text = "部门编辑"
        imgRight.visibility = View.VISIBLE
        imgRight.setImageResource(R.drawable.finish)
        back.setOnClickListener { finish() }
        btnDel.setOnClickListener {
            showHintDialog(department)
        }
        imgRight.setOnClickListener {
            if (TextUtils.equals(etName.text, department.name)) {
                ToastUtils.show("没有修改")
            } else {
                updateDepartment(department, etName.text.toString())
            }
        }
    }

    private fun updateDepartment(department: DepartmentBean, name: String) {
        var departments = RetrofitManager.retrofitManager.getRetrofit()
                .create(ApiService::class.java).updateDepartment(department.id, name)
        HttpRxObservable.getObservable(departments, this).subscribe(object : HttpRxObserver<Any>() {
            override fun onStart(d: Disposable) {
            }

            override fun onError(e: ApiException) {
                ToastUtils.show(e.msg)
            }

            override fun onSuccess(response: Any) {
                ToastUtils.show("$name  修改成功")
                finish()
            }

        })
    }

    private fun showHintDialog(department: DepartmentBean) {
        var messageHint: DialogMessageHint = DialogMessageHint.Builder(this)
                .setHtmlMode(false)
                .setMsg("确认要删除${department.name} 部门吗？删除之后不可恢复！")
                .build()
        messageHint.setOnClickListener(object : DialogMessageHint.OnClickListener {
            override fun onLeftClick() {
            }

            override fun onRightClick() {
                deleteDepartment(department)
            }

        })
        messageHint.show()
    }

    private fun deleteDepartment(department: DepartmentBean) {
        var departments = RetrofitManager.retrofitManager.getRetrofit()
                .create(ApiService::class.java).delDepartment(department.id)
        HttpRxObservable.getObservable(departments, this).subscribe(object : HttpRxObserver<Any>() {
            override fun onStart(d: Disposable) {
            }

            override fun onError(e: ApiException) {
                ToastUtils.show(e.msg)
            }

            override fun onSuccess(response: Any) {
                ToastUtils.show("${department.name}  删除成功")
                finish()
            }

        })
    }
}