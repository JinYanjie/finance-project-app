package com.kangce.finance.ui.manager.salary

import android.content.Context
import android.content.Intent
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.RecyclerView
import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.hjq.toast.ToastUtils
import com.kangce.finance.base.BaseList.BaseListActivity
import com.kangce.finance.R
import com.kangce.finance.bean.SalaryUserBean
import com.kangce.finance.http.exceptition.ApiException
import com.kangce.finance.http.observer.HttpRxObservable
import com.kangce.finance.http.observer.HttpRxObserver
import com.kangce.finance.http.ohkttp.RetrofitManager
import com.kangce.finance.http.service.ApiService
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_salary.*
import kotlinx.android.synthetic.main.title.*

class SalaryActivity : BaseListActivity<SalaryUserBean>() {

    override fun getLayoutId(): Int {
        return R.layout.activity_salary
    }

    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, SalaryActivity::class.java))
        }
    }

    override fun initView() {
        super.initView()
        back.setOnClickListener { finish() }
        tvTitle.text = "薪资管理"
        imgRight.visibility = View.VISIBLE
        imgRight.setImageResource(R.drawable.add)
        imgRight.setOnClickListener {
            UnSalaryActivity.start(this)
        }
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
        getEnterStaff()
    }

    override fun onItemClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {


    }

    private fun getEnterStaff() {
        var staff = RetrofitManager.retrofitManager.getRetrofit()
                .create(ApiService::class.java).getEnterStaff(mPageNum,PAGE_SIZE)
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

    class Adapter : BaseQuickAdapter<SalaryUserBean, BaseViewHolder> {
        constructor() : super(R.layout.item_salary_staff)

        override fun convert(helper: BaseViewHolder?, item: SalaryUserBean?) {
            if (item != null) {
                with(item) {
                    helper?.setText(R.id.tvStaffId, id)
                    helper?.setText(R.id.tvStaffName, name)
                }
            }
        }
    }
}