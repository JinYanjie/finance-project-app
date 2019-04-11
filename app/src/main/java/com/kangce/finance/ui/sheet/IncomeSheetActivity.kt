package com.kangce.finance.ui.sheet

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.RelativeLayout
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.hjq.toast.ToastUtils
import com.kangce.finance.R
import com.kangce.finance.base.BaseActivity
import com.kangce.finance.bean.IncomeSheetBean
import com.kangce.finance.http.exceptition.ApiException
import com.kangce.finance.http.observer.HttpRxObservable
import com.kangce.finance.http.observer.HttpRxObserver
import com.kangce.finance.http.ohkttp.RetrofitManager
import com.kangce.finance.http.service.ApiService
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_income_sheet.*
import kotlinx.android.synthetic.main.title.*

class IncomeSheetActivity : BaseActivity() {
    val adapter: Adapter by lazy { Adapter() }
    override fun getLayoutId(): Int {
        return R.layout.activity_income_sheet
    }

    companion object {
        fun start(context: Context?) {
            context?.startActivity(Intent(context, IncomeSheetActivity::class.java))
        }
    }

    override fun initView() {
        super.initView()
        back.setOnClickListener { finish() }
        imgRight.visibility = View.GONE
        tvTitle.text = "损益表"

        rvIncome.layoutManager = LinearLayoutManager(this)
        rvIncome.adapter=adapter
        getSheetData()
    }

    private fun getSheetData() {
        var balanceSheet = RetrofitManager.retrofitManager.getRetrofit().create(ApiService::class.java).getIncomeSheet()
        HttpRxObservable.getObservable(balanceSheet, this).subscribe(object : HttpRxObserver<List<IncomeSheetBean>>() {
            override fun onStart(d: Disposable) {
            }

            override fun onError(e: ApiException) {
                ToastUtils.show(e.msg)
            }

            override fun onSuccess(response: List<IncomeSheetBean>) {
                adapter.setNewData(response)
            }
        })
    }


    class Adapter : BaseQuickAdapter<IncomeSheetBean, BaseViewHolder> {
        constructor() : super(R.layout.item_balance_sheet)

        override fun convert(helper: BaseViewHolder?, item: IncomeSheetBean?) {
            if (item != null) {
                with(item) {
                    when (hierarchy) {
                        0 -> {

                            helper?.getView<TextView>(R.id.tvLevel1)?.visibility = View.VISIBLE
                            helper?.getView<TextView>(R.id.tvLevel2)?.visibility = View.GONE
                            helper?.getView<TextView>(R.id.tvLevel3)?.visibility = View.GONE

                            helper?.getView<TextView>(R.id.tvNum)?.setTextColor(Color.parseColor("#ff6600"))
                            helper?.getView<TextView>(R.id.tvLevel1)?.setTextColor(Color.parseColor("#ff6600"))

                            helper?.setText(R.id.tvLevel1, name)
                        }
                        1 -> {
                            helper?.getView<TextView>(R.id.tvLevel1)?.visibility = View.GONE
                            helper?.getView<TextView>(R.id.tvLevel2)?.visibility = View.VISIBLE
                            helper?.getView<TextView>(R.id.tvLevel3)?.visibility = View.GONE


                            helper?.setText(R.id.tvLevel2, name)
                        }
                        2 -> {

                            helper?.getView<TextView>(R.id.tvLevel1)?.visibility = View.GONE
                            helper?.getView<TextView>(R.id.tvLevel2)?.visibility = View.GONE
                            helper?.getView<TextView>(R.id.tvLevel3)?.visibility = View.VISIBLE
                            helper?.getView<TextView>(R.id.tvNum)?.setTextColor(Color.parseColor("#333333"))
                            helper?.getView<TextView>(R.id.tvLevel3)?.setTextColor(Color.parseColor("#333333"))
                            helper?.setText(R.id.tvLevel3, name)
                        }
                        else -> {
                            helper?.getView<RelativeLayout>(R.id.rlItem)?.visibility = View.GONE
                        }
                    }
                }
            }
        }
    }
}