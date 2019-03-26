package com.kangce.finance.ui.manager.salary

import android.content.Context
import android.content.Intent
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.RecyclerView
import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.kangce.finance.R
import com.kangce.finance.base.BaseList.BaseListActivity
import com.kangce.finance.bean.SBean
import kotlinx.android.synthetic.main.activity_un_salary.*
import kotlinx.android.synthetic.main.title.*

//class UnSalaryActivity : BaseListActivity<SBean>() {
//
//
//    override fun getLayoutId(): Int {
//        return R.layout.activity_un_salary
//    }
//
//    companion object {
//        fun start(context: Context) {
//            val intent = Intent(context, UnSalaryActivity::class.java)
//            context.startActivity(intent)
//        }
//    }
//
//    override fun initView() {
//        super.initView()
//        back.setOnClickListener { finish() }
//
//        tvTitle.text = "未录入薪资员工"
//        imgRight.visibility = View.GONE
//
//    }
//
////    override fun bindAdapter(): BaseQuickAdapter<SBean, BaseViewHolder> {
////
////    }
//
//    override fun getRecyclerView(): RecyclerView? {
//        return rvSalary
//    }
//
//    override fun getSwipeRefreshLayout(): SwipeRefreshLayout? {
//        return refreshSalary
//    }
//
//    override fun loadListData() {
//
//    }
//
//    override fun onItemClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
//    }
//
//
//}