package com.kangce.finance.ui.sheet

import android.content.Context
import android.content.Intent
import android.view.View
import com.hjq.toast.ToastUtils
import com.kangce.finance.R
import com.kangce.finance.base.BaseActivity
import com.kangce.finance.bean.AllocateSalaryBean
import com.kangce.finance.bean.IncomeSheetBean
import com.kangce.finance.http.exceptition.ApiException
import com.kangce.finance.http.observer.HttpRxObservable
import com.kangce.finance.http.observer.HttpRxObserver
import com.kangce.finance.http.ohkttp.RetrofitManager
import com.kangce.finance.http.service.ApiService
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_allcoate_sheet.*
import kotlinx.android.synthetic.main.title.*
import lecho.lib.hellocharts.model.SliceValue
import lecho.lib.hellocharts.model.PieChartData
import android.graphics.Color

class AllocateActivity : BaseActivity() {
    override fun getLayoutId(): Int {
        return com.kangce.finance.R.layout.activity_allcoate_sheet
    }

    companion object {
        fun start(context: Context?) {
            context?.startActivity(Intent(context, AllocateActivity::class.java))
        }
    }

    override fun initView() {
        super.initView()
        back.setOnClickListener { finish() }
        imgRight.visibility = View.GONE
        tvTitle.text = "工资分配表"

        getAllocateData()
    }

    private fun getAllocateData() {
        var allocateSheet = RetrofitManager.retrofitManager.getRetrofit().create(ApiService::class.java).getAllocateSheet()
        HttpRxObservable.getObservable(allocateSheet, this).subscribe(object : HttpRxObserver<List<AllocateSalaryBean>>() {
            override fun onStart(d: Disposable) {
            }

            override fun onError(e: ApiException) {
                ToastUtils.show(e.msg)
            }

            override fun onSuccess(response: List<AllocateSalaryBean>) {
                val values = mutableListOf<SliceValue>()
                val colors = listOf<String>("#aaff6600", "#aa51d1bb", "#aa00ADED", "#aaffb700", "#aaff6933")

                for (i in 0 until response.size) {
                    var value = SliceValue(response[i].depSalary.toFloat(), Color.parseColor(colors[i]))
                    value.setLabel("${response[i].name}    ${response[i].depSalary}")
                    values.add(value)
                }


                var mPieChartData = PieChartData(values)
                mPieChartData.setHasLabels(true) //是否有标签，，默认在内部
                mPieChartData.setHasLabelsOnlyForSelected(false) //是否设置选中时是否有标签
                mPieChartData.setHasLabelsOutside(true) //是否设置标签在外部
                mPieChartData.setHasCenterCircle(false) // 是否有中心圆环
                mPieChartData.centerCircleScale = 0.4f//设置中心环的大小
                mPieChartData.slicesSpacing = 2  //设置分离间距--


                //设置控件相关
                mPieChartView.pieChartData = mPieChartData    //设置数据
                mPieChartView.isValueSelectionEnabled = true   //设置是否能选中
                mPieChartView.startDataAnimation()         //设置动画
                mPieChartView.circleFillRatio = 0.8f//设置饼状图占整个view的比例
                mPieChartView.isViewportCalculationEnabled = true//设置饼图自动适应大小
                mPieChartView.isChartRotationEnabled = true//设置饼图是否可以手动旋转
            }
        })
    }
}