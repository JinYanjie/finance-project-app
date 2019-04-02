package com.kangce.finance.ui.manager.salary

import android.content.Context
import android.content.Intent
import android.text.Editable
import android.text.TextWatcher
import com.hjq.toast.ToastUtils
import com.kangce.finance.base.BaseActivity
import com.kangce.finance.R
import com.kangce.finance.bean.SalaryUserBean
import com.kangce.finance.bean.SocialRateBean
import com.kangce.finance.http.exceptition.ApiException
import com.kangce.finance.http.observer.HttpRxObservable
import com.kangce.finance.http.observer.HttpRxObserver
import com.kangce.finance.http.ohkttp.RetrofitManager
import com.kangce.finance.http.service.ApiService
import com.kangce.finance.widget.MyTextWatcher
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_add_salary.*
import java.text.SimpleDateFormat
import java.util.*


class AddSalaryActivity : BaseActivity() {
    override fun getLayoutId(): Int {
        return R.layout.activity_add_salary
    }

    private var salaryUserBean: SalaryUserBean? = null
    private var socialRateBeans: List<SocialRateBean>? = null

    companion object {
        fun start(context: Context, salaryUserBean: SalaryUserBean) {
            val intent = Intent(context, AddSalaryActivity::class.java)
            intent.putExtra("salaryUserBean", salaryUserBean)
            context.startActivity(intent)
        }
    }

    override fun initView() {
        super.initView()
        salaryUserBean = intent.getSerializableExtra("salaryUserBean") as SalaryUserBean
        getSocialRate()
        if (salaryUserBean != null) {
            tvName.setText(salaryUserBean!!.name)
            tvSId.setText(salaryUserBean!!.id.toString())
            tvState.setText(if (salaryUserBean!!.state == 1) "在职" else "离职")
            tvDate.setText(SimpleDateFormat("yy-MM-dd", Locale.CHINA).format(Date()))
            //公积金基数
            tvReservedFundsRate.addTextChangedListener(object : MyTextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    //需交公积金
                    tvReservedFunds.setText(String.format("#.2f", s.toString().toDouble() * socialRateBeans!![3].rate))
                }
            })




            tvPensionRate.addTextChangedListener(object : MyTextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    //养老保险基数
                    tvReservedFunds.setText(String.format("#.2f", s.toString().toDouble() * socialRateBeans!![0].rate))
                }
            })

            tvMedicalRate.addTextChangedListener(object : MyTextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    //养老保险基数
                    tvMedical.setText(String.format("#.2f", s.toString().toDouble() * socialRateBeans!![1].rate))
                }
            })
            tvLossJobRate.addTextChangedListener(object : MyTextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    //养老保险基数
                    tvMedical.setText(String.format("#.2f", s.toString().toDouble() * socialRateBeans!![2].rate))
                }
            })



        }
    }

    private fun getSocialRate() {
        var rate = RetrofitManager.retrofitManager.getRetrofit().create(ApiService::class.java).getRate()
        HttpRxObservable.getObservable(rate, this).subscribe(object : HttpRxObserver<List<SocialRateBean>>() {
            override fun onStart(d: Disposable) {
            }

            override fun onError(e: ApiException) {
                ToastUtils.show(e.msg)
            }

            override fun onSuccess(response: List<SocialRateBean>) {
                // {"id":1,"name":"养老保险","rate":0.08,"type":0},
                // {"id":2,"name":"医疗保险","rate":0.02,"type":0},
                // {"id":3,"name":"失业保险","rate":0.005,"type":0},
                // {"id":4,"name":"住房公积金","rate":0.1,"type":1},
                // {"id":5,"name":"扣税基数","rate":3500.0,"type":2}
                socialRateBeans = response

            }

        })
    }
}