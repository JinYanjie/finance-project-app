package com.kangce.finance.ui.manager.salary

import android.content.Context
import android.content.Intent
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.RecyclerView
import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.hjq.toast.ToastUtils
import com.kangce.finance.base.BaseActivity
import com.kangce.finance.base.BaseList.BaseListActivity
import com.kangce.finance.bean.SBean
import com.kangce.finance.bean.Staff
import com.kangce.finance.bean.StaffBeanShorts
import com.kangce.finance.choumou.R
import com.kangce.finance.choumou.http.exceptition.ApiException
import com.kangce.finance.choumou.http.observer.HttpRxObservable
import com.kangce.finance.choumou.http.observer.HttpRxObserver
import com.kangce.finance.choumou.http.ohkttp.RetrofitManager
import com.kangce.finance.choumou.http.service.ApiService
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_salary.*

class SalaryActivity : BaseListActivity<SBean>() {

    override fun getLayoutId(): Int {
        return R.layout.activity_salary
    }

    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, SalaryActivity::class.java))
        }
    }

    override fun bindAdapter(): BaseQuickAdapter<SBean, BaseViewHolder> {
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
        var staff = RetrofitManager.retrofitManager.getRetrofit(this)
                .create(ApiService::class.java).getEnterStaff()
        HttpRxObservable.getObservable(staff, this).subscribe(object : HttpRxObserver<List<SBean>>() {
            override fun onStart(d: Disposable) {
            }

            override fun onError(e: ApiException) {
                ToastUtils.show(e.msg)
            }

            override fun onSuccess(response: List<SBean>) {
                onSetLoadData(response)
            }

        })
    }

    class Adapter : BaseQuickAdapter<SBean, BaseViewHolder> {
        constructor() : super(R.layout.item_salary_staff)

        override fun convert(helper: BaseViewHolder?, item: SBean?) {
            if (item != null) {
                with(item) {
                    helper?.setText(R.id.tvId, id)
                    helper?.setText(R.id.tvName, name)
                }
            }
        }


    }
}