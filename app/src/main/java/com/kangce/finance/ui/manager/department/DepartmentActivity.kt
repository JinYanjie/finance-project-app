package com.kangce.finance.ui.manager.department

import android.content.Context
import android.content.Intent
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.hjq.toast.ToastUtils
import com.kangce.finance.base.BaseActivity
import com.kangce.finance.bean.DepartmentBean
import com.kangce.finance.choumou.R
import com.kangce.finance.choumou.http.exceptition.ApiException
import com.kangce.finance.choumou.http.observer.HttpRxObservable
import com.kangce.finance.choumou.http.observer.HttpRxObserver
import com.kangce.finance.choumou.http.ohkttp.RetrofitManager
import com.kangce.finance.choumou.http.service.ApiService
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_department.*
import kotlinx.android.synthetic.main.title.*

class DepartmentActivity : BaseActivity() {
    var adapter: Adapter? = null
    var isAskData: Boolean = false
    override fun getLayoutId(): Int {
        return R.layout.activity_department
    }

    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, DepartmentActivity::class.java))
        }
    }

    override fun initView() {
        super.initView()
        back.setOnClickListener { finish() }
        tvTitle.text = "部门管理"
        imgRight.visibility = View.VISIBLE
        imgRight.setImageResource(R.drawable.add)
        imgRight.setOnClickListener { AddDepartmentActivity.start(this) }
        dRecycleView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        adapter = Adapter()
        dRecycleView.adapter = adapter
        adapter?.setOnItemClickListener { adapter, view, position ->
            DeleteDepartmentActivity.start(this, adapter.data[position] as DepartmentBean)
        }

    }

    override fun initData() {
        super.initData()
        getDepartment()
    }


    override fun onResume() {
        super.onResume()
        if (isAskData) {
            getDepartment()
            isAskData = false
        }
    }

    override fun onPause() {
        super.onPause()
        isAskData = true
    }


    private fun getDepartment() {
        var departments = RetrofitManager.retrofitManager.getRetrofit(this)
                .create(ApiService::class.java).getAllDepartment()
        HttpRxObservable.getObservable(departments, this).subscribe(object : HttpRxObserver<List<DepartmentBean>>() {
            override fun onStart(d: Disposable) {
            }

            override fun onError(e: ApiException) {
                ToastUtils.show(e.msg)
            }

            override fun onSuccess(response: List<DepartmentBean>) {
                adapter?.setNewData(response)
            }

        })
    }

    class Adapter : BaseQuickAdapter<DepartmentBean, BaseViewHolder> {
        constructor() : super(R.layout.item_department)

        override fun convert(helper: BaseViewHolder?, item: DepartmentBean?) {
            if (item != null) {
                helper?.setText(R.id.tvName, item.name)
            }
        }
    }
}