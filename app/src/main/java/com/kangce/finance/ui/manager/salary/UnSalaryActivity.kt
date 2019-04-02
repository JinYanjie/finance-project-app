package com.kangce.finance.ui.manager.salary

import android.content.Context
import android.content.Intent
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.hjq.toast.ToastUtils
import com.kangce.finance.R
import com.kangce.finance.base.BaseList.BaseListActivity
import com.kangce.finance.bean.SalaryUserBean
import com.kangce.finance.http.exceptition.ApiException
import com.kangce.finance.http.observer.HttpRxObservable
import com.kangce.finance.http.observer.HttpRxObserver
import com.kangce.finance.http.ohkttp.RetrofitManager
import com.kangce.finance.http.service.ApiService
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_un_salary.*
import kotlinx.android.synthetic.main.title.*

/**
 * 未添加工资的员工
 */
class UnSalaryActivity : BaseListActivity<SalaryUserBean>() {

    override fun getLayoutId(): Int {
        return R.layout.activity_un_salary
    }

    companion object {
        fun start(context: Context) {
            val intent = Intent(context, UnSalaryActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun initView() {
        super.initView()
        back.setOnClickListener { finish() }

        tvTitle.text = "未录入薪资员工"
        imgRight.visibility = View.GONE

    }

    override fun bindAdapter(): BaseQuickAdapter<SalaryUserBean, BaseViewHolder> {
        return Adapter()
    }

    override fun getRecyclerView(): RecyclerView? {
        return rvSalary
    }

    override fun getSwipeRefreshLayout(): SwipeRefreshLayout? {
        return refreshSalary
    }

    override fun loadListData() {
        getUnSalaryStaff()
    }

    override fun onItemClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
        var list = adapter?.data as List<SalaryUserBean>
        AddSalaryActivity.start(this, list[position])
    }

    private fun getUnSalaryStaff() {
        var staff = RetrofitManager.retrofitManager.getRetrofit()
                .create(ApiService::class.java).getUnSalaryStaff(mPageNum, PAGE_SIZE)
        HttpRxObservable.getObservable(staff, this).subscribe(object : HttpRxObserver<List<SalaryUserBean>>() {
            override fun onStart(d: Disposable) {
            }

            override fun onError(e: ApiException) {
                ToastUtils.show(e.msg)
            }

            override fun onSuccess(response: List<SalaryUserBean>) {
                onSetLoadData(response)
            }

        })
    }

//========================================================================================
    class Adapter : BaseQuickAdapter<SalaryUserBean, BaseViewHolder> {
        constructor() : super(R.layout.item_salary_staff)
        override fun convert(helper: BaseViewHolder?, item: SalaryUserBean?) {
            if (item != null) {
                with(item) {
                    var vId = helper?.getView<TextView>(R.id.tvStaffId)
                    var vName = helper?.getView<TextView>(R.id.tvStaffName)
                    vId?.text = id.toString()
                    vName?.text = name
                }
            }
        }
    }
}