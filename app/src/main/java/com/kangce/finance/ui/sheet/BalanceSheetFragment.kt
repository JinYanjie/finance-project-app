package com.kangce.finance.ui.sheet

import android.graphics.Color
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.RelativeLayout
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.hjq.toast.ToastUtils
import com.kangce.finance.R
import com.kangce.finance.base.BaseFragment
import com.kangce.finance.bean.BalanceSheetBean
import com.kangce.finance.http.exceptition.ApiException
import com.kangce.finance.http.observer.HttpRxObservable
import com.kangce.finance.http.observer.HttpRxObserver
import com.kangce.finance.http.ohkttp.RetrofitManager
import com.kangce.finance.http.service.ApiService
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.fragment_balance_sheet.*

class BalanceSheetFragment : BaseFragment() {
    override fun getLayoutId(): Int {
        return R.layout.fragment_balance_sheet
    }

    val adapter: Adapter by lazy { Adapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var bundle = arguments?.getInt("bundle")
        getSheetData(bundle!!)
        rvBalanceSheet.layoutManager = LinearLayoutManager(context)
        rvBalanceSheet.adapter = adapter
    }

    private fun getSheetData(type: Int) {
        var balanceSheet = RetrofitManager.retrofitManager.getRetrofit().create(ApiService::class.java).getBalanceSheet(type)
        HttpRxObservable.getObservable(balanceSheet, this).subscribe(object : HttpRxObserver<List<BalanceSheetBean>>() {
            override fun onStart(d: Disposable) {
            }

            override fun onError(e: ApiException) {
                ToastUtils.show(e.msg)
            }

            override fun onSuccess(response: List<BalanceSheetBean>) {
                adapter.setNewData(response)
            }
        })
    }

    class Adapter : BaseQuickAdapter<BalanceSheetBean, BaseViewHolder> {
        constructor() : super(R.layout.item_balance_sheet)

        override fun convert(helper: BaseViewHolder?, item: BalanceSheetBean?) {
            if (item != null) {
                with(item) {
                    when (hierarchy) {
                        1 -> {
                            helper?.getView<RelativeLayout>(R.id.rlItem)?.visibility = View.VISIBLE
                            helper?.getView<TextView>(R.id.tvLevel1)?.visibility = View.VISIBLE
                            helper?.getView<TextView>(R.id.tvLevel2)?.visibility = View.GONE
                            helper?.getView<TextView>(R.id.tvLevel3)?.visibility = View.GONE
                            helper?.setText(R.id.tvLevel1, name)
                        }
                        2 -> {
                            helper?.getView<RelativeLayout>(R.id.rlItem)?.visibility = View.VISIBLE
                            helper?.getView<TextView>(R.id.tvLevel1)?.visibility = View.GONE
                            helper?.getView<TextView>(R.id.tvLevel2)?.visibility = View.VISIBLE
                            helper?.getView<TextView>(R.id.tvLevel3)?.visibility = View.GONE
                            helper?.setText(R.id.tvLevel2, name)
                        }
                        3 -> {
                            helper?.getView<RelativeLayout>(R.id.rlItem)?.visibility = View.VISIBLE
                            helper?.getView<TextView>(R.id.tvLevel1)?.visibility = View.GONE
                            helper?.getView<TextView>(R.id.tvLevel2)?.visibility = View.GONE
                            helper?.getView<TextView>(R.id.tvLevel3)?.visibility = View.VISIBLE
                            helper?.setText(R.id.tvLevel3, name)
                            helper?.getView<TextView>(R.id.tvNum)?.setTextColor(Color.parseColor("#ff6600"))
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