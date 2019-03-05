package com.kangce.finance.ui.main.manager.department

import android.content.Context
import android.content.Intent
import android.support.v7.widget.LinearLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.kangce.finance.base.BaseActivity
import com.kangce.finance.bean.DepartmentBean
import com.kangce.finance.choumou.R
import com.kangce.finance.choumou.http.exceptition.ApiException
import com.kangce.finance.choumou.http.observer.HttpRxObservable
import com.kangce.finance.choumou.http.observer.HttpRxObserver
import com.kangce.finance.choumou.http.ohkttp.RetrofitManager
import com.kangce.finance.choumou.http.service.ApiService
import com.kangce.finance.utils.L
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_department.*
import kotlinx.android.synthetic.main.title.*

class DepartmentActivity : BaseActivity() {
    var adapter: Adapter? = null
    override fun getLayoutId(): Int {
        return R.layout.activity_department
    }
    companion object {
        fun start(context: Context){
            context.startActivity(Intent(context,DepartmentActivity::class.java))
        }
    }

    override fun initView() {
        super.initView()
        back.setOnClickListener { finish() }
        tvTitle.text="部门管理"
        dRecycleView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        adapter = Adapter()
        dRecycleView.adapter = adapter
        adapter?.setOnItemClickListener { adapter, view, position -> }
    }

    override fun initData() {
        super.initData()
        getDepartment()
    }


    private fun getDepartment() {
        var departments = RetrofitManager.retrofitManager.getRetrofit(this)
                .create(ApiService::class.java).getAllDepartment()
        HttpRxObservable.getObservable(departments, this).subscribe(object : HttpRxObserver<List<DepartmentBean>>() {
            override fun onStart(d: Disposable) {
                L.d("start")
            }

            override fun onError(e: ApiException) {

                L.e(e.toString())
            }

            override fun onSuccess(response: List<DepartmentBean>) {
                L.e(response.toString())
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