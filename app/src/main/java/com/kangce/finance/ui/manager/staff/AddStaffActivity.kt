package com.kangce.finance.ui.manager.staff

import android.content.Context
import android.content.Intent

import android.text.TextUtils
import android.text.TextWatcher
import android.view.View
import com.google.gson.Gson
import com.hjq.toast.ToastUtils
import com.kangce.finance.base.BaseActivity
import com.kangce.finance.bean.DepartmentBean
import com.kangce.finance.bean.StaffBean
import com.kangce.finance.R
import com.kangce.finance.http.exceptition.ApiException
import com.kangce.finance.http.observer.HttpRxObservable
import com.kangce.finance.http.observer.HttpRxObserver
import com.kangce.finance.http.ohkttp.RetrofitManager
import com.kangce.finance.http.service.ApiService
import com.kangce.finance.utils.L
import com.kangce.finance.widget.EditTextClear
import com.kangce.finance.widget.PopupWindowList
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_add_staff.*
import kotlinx.android.synthetic.main.title.*
import okhttp3.RequestBody


class AddStaffActivity : BaseActivity() {
    private var mPopList: PopupWindowList? = null
    private var listDepName = mutableListOf<String>()
    private var listDep = mutableListOf<DepartmentBean>()
    override fun getLayoutId(): Int {
        return R.layout.activity_add_staff
    }

    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, AddStaffActivity::class.java))
        }
    }

    override fun initView() {
        super.initView()
        tvTitle.text = "添加员工"
        imgRight.visibility = View.VISIBLE
        imgRight.setImageResource(R.drawable.finish)
        back.setOnClickListener { finish() }
        //获取部门数据
        getDepartment()
        mPopList = PopupWindowList(this)
        department.setOnClickListener {
            mPopList?.show(listDepName, department) {
                department.text = it
            }
        }

        imgRight.setOnClickListener {
            if (TextUtils.isEmpty(name.text)) {
                ToastUtils.show("姓名不能为空")
                return@setOnClickListener
            }
            if (TextUtils.isEmpty(phone.text)) {
                ToastUtils.show("手机号不能为空")
                return@setOnClickListener
            }
            if (TextUtils.isEmpty(gender.text)) {
                ToastUtils.show("性别不能为空")
                return@setOnClickListener
            }
            if (TextUtils.isEmpty(inDate.text)) {
                ToastUtils.show("入职日期不能为空")
                return@setOnClickListener
            }
            if (TextUtils.isEmpty(idCard.text)) {
                ToastUtils.show("身份证不能为空")
                return@setOnClickListener
            }
            if (TextUtils.isEmpty(bankNum.text)) {
                ToastUtils.show("银行卡不能为空")
                return@setOnClickListener
            }


            val staffBean = StaffBean(
                    bankName.text.toString(),
                    bankNum.text.toString(),
                    birthday.text.toString(),
                    birthplace.text.toString(),
                    getDepId(department.text.toString()),
                    education.text.toString().toInt(),//需要根据教育改动
                    gender.text.toString(),
                    0,
                    idCard.text.toString(),
                    inDate.text.toString(),
                    job.text.toString(),
                    name.text.toString(),
                    nation.text.toString(),
                    outDate.text.toString(),
                    phone.text.toString(),
                    position.text.toString().toInt(),////需要根据教育改动
                    state.text.toString().toInt())//需要根据教育改动

            addStaff(staffBean)
        }


    }

    private fun addStaff(staffBean: StaffBean) {
        var toJson = Gson().toJson(staffBean)
        var create = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), toJson)
        var departments = RetrofitManager.retrofitManager.getRetrofit()
                .create(ApiService::class.java).addStaff(create)
        HttpRxObservable.getObservable(departments, this).subscribe(object : HttpRxObserver<StaffBean>() {
            override fun onStart(d: Disposable) {
                L.d("start")
            }

            override fun onError(e: ApiException) {
                ToastUtils.show(e.msg)
            }

            override fun onSuccess(response: StaffBean) {
                ToastUtils.show("${response.name}  添加成功")

            }
        })
    }

    private fun getDepartment() {
        var departments = RetrofitManager.retrofitManager.getRetrofit()
                .create(ApiService::class.java).getAllDepartment()
        HttpRxObservable.getObservable(departments, this).subscribe(object : HttpRxObserver<List<DepartmentBean>>() {
            override fun onStart(d: Disposable) {
            }

            override fun onError(e: ApiException) {
                ToastUtils.show(e.msg)
            }

            override fun onSuccess(response: List<DepartmentBean>) {
                listDep = response as MutableList<DepartmentBean>
                for (departmentBean in response) {
                    listDepName.add(departmentBean.name)
                }
            }

        })
    }

    //根据部门返回id
    fun getDepId(name: String): Int {
        for (departmentBean in listDep) {
            if (TextUtils.equals(departmentBean.name, name)) {
                return departmentBean.id
            }
        }
        return 0
    }
}