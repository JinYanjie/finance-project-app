package com.kangce.finance.ui.manager.staff

import android.content.Context
import android.content.Intent
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.hjq.toast.ToastUtils
import com.kangce.finance.base.BaseActivity
import com.kangce.finance.base.BaseList.BaseListActivity
import com.kangce.finance.bean.DepartmentBean
import com.kangce.finance.bean.Staff
import com.kangce.finance.bean.StaffBean
import com.kangce.finance.R
import com.kangce.finance.R.drawable.finish
import com.kangce.finance.R.id.*
import com.kangce.finance.http.exceptition.ApiException
import com.kangce.finance.http.observer.HttpRxObservable
import com.kangce.finance.http.observer.HttpRxObserver
import com.kangce.finance.http.ohkttp.RetrofitManager
import com.kangce.finance.http.service.ApiService
import com.kangce.finance.ui.manager.department.AddDepartmentActivity
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.title.*

class StaffActivity : BaseListActivity<StaffBean>() {


    override fun bindAdapter(): BaseQuickAdapter<StaffBean, BaseViewHolder> {
        return Adapter()
    }


    override fun getLayoutId(): Int {
        return R.layout.activity_staff
    }


    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, StaffActivity::class.java))
        }
    }

    override fun initView() {
        super.initView()
        back.setOnClickListener { finish() }
        tvTitle.text = "职员管理"
        imgRight.visibility = View.VISIBLE
        imgRight.setImageResource(R.drawable.add)
        imgRight.setOnClickListener { AddStaffActivity.start(this) }

    }


    private fun getStaff() {
        var staff = RetrofitManager.retrofitManager.getRetrofit()
                .create(ApiService::class.java).getAllStaff(mPageNum, PAGE_SIZE)
        HttpRxObservable.getObservable(staff, this).subscribe(object : HttpRxObserver<Staff>() {
            override fun onStart(d: Disposable) {
            }

            override fun onError(e: ApiException) {
                ToastUtils.show(e.msg)
            }

            override fun onSuccess(response: Staff) {
                onSetLoadData(response.list)
            }

        })
    }

    class Adapter : BaseQuickAdapter<StaffBean, BaseViewHolder> {
        constructor() : super(R.layout.item_department)

        override fun convert(helper: BaseViewHolder?, item: StaffBean?) {
            if (item != null) {
                helper?.setText(R.id.tvName, item.name)
            }
        }
    }


    override fun getRecyclerView(): RecyclerView {
        return findViewById(R.id.recStaff)
    }

    override fun getSwipeRefreshLayout(): SwipeRefreshLayout? {
        return findViewById(R.id.refreshStaff)
    }


    override fun loadListData() {
        getStaff()
    }

    override fun onItemClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
    }
}