package com.kangce.finance.ui.manager.salary

import android.content.Context
import android.content.Intent
import android.text.Editable
import android.text.TextUtils
import android.view.View
import com.google.gson.Gson
import com.hjq.toast.ToastUtils
import com.kangce.finance.base.BaseActivity
import com.kangce.finance.R
import com.kangce.finance.bean.SalaryBean
import com.kangce.finance.bean.SalaryUserBean
import com.kangce.finance.bean.SocialRateBean
import com.kangce.finance.http.exceptition.ApiException
import com.kangce.finance.http.observer.HttpRxObservable
import com.kangce.finance.http.observer.HttpRxObserver
import com.kangce.finance.http.ohkttp.RetrofitManager
import com.kangce.finance.http.service.ApiService
import com.kangce.finance.utils.L
import com.kangce.finance.utils.RatePersonalUtil
import com.kangce.finance.widget.MyTextWatcher
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_add_salary.*
import kotlinx.android.synthetic.main.title.*
import okhttp3.RequestBody
import java.text.DecimalFormat
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
        tvTitle.text = "添加工资"
        back.setOnClickListener { finish() }
        imgRight.visibility = View.VISIBLE
        imgRight.setImageResource(R.drawable.finish)
        salaryUserBean = intent.getSerializableExtra("salaryUserBean") as SalaryUserBean
        getSocialRate()
        imgRight.setOnClickListener {
            getTextsValue()
        }
        if (salaryUserBean != null) {
            tvName.setText(salaryUserBean!!.name)

            tvSId.setText(salaryUserBean!!.id.toString())
            tvState.setText(if (salaryUserBean!!.state == 1) "在职" else "离职")
            tvDate.setText(SimpleDateFormat("yy-MM-dd", Locale.CHINA).format(Date()))


            //公积金基数
            tvReservedFundsRate.addTextChangedListener(object : MyTextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    //需交公积金
                    if (TextUtils.isEmpty(s)) {
                        tvReservedFunds.setText("0")
                    } else {
                        tvReservedFunds.setText(DecimalFormat("0.00").format(s.toString().toDouble() * socialRateBeans!![3].rate))
                    }
                }
            })



            tvPensionRate.addTextChangedListener(object : MyTextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    //养老保险基数

                    if (TextUtils.isEmpty(s)) {
                        tvPension.setText("0")
                    } else {
                        tvPension.setText(DecimalFormat("0.00").format(s.toString().toDouble() * socialRateBeans!![0].rate))
                    }
                }
            })

            tvMedicalRate.addTextChangedListener(object : MyTextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    //养老保险基数

                    if (TextUtils.isEmpty(s)) {
                        tvMedical.setText("0")
                    } else {
                        tvMedical.setText(DecimalFormat("0.00").format(s.toString().toDouble() * socialRateBeans!![1].rate))
                    }

                }
            })
            tvLossJobRate.addTextChangedListener(object : MyTextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    //失业保险

                    if (TextUtils.isEmpty(s)) {
                        tvLossJob.setText("0")
                    } else {
                        tvLossJob.setText(DecimalFormat("0.00").format(s.toString().toDouble() * socialRateBeans!![2].rate))
                    }
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

    private fun getTextsValue() {
        if (TextUtils.isEmpty(tvBase.text)) {
            ToastUtils.show("请输入基本工资")
            return
        }
        if (TextUtils.isEmpty(tvJobSalary.text)) {
            ToastUtils.show("请输入岗位工资")
            return
        }

        if (TextUtils.isEmpty(tvAddSalary.text)) {
            tvAddSalary.setText("0")
        }
        if (TextUtils.isEmpty(tvPerformSalary.text)) {
            tvPerformSalary.setText("0")
        }
        if (TextUtils.isEmpty(tvBonus.text)) {
            tvBonus.setText("0")
        }
        if (TextUtils.isEmpty(tvWelfare.text)) {
            tvWelfare.setText("0")
        }
        if (TextUtils.isEmpty(tvChildEdu.text)) {
            tvChildEdu.setText("0")
        }
        if (TextUtils.isEmpty(tvContinueEdu.text)) {
            tvContinueEdu.setText("0")
        }
        if (TextUtils.isEmpty(tvBigDisease.text)) {
            tvBigDisease.setText("0")
        }
        if (TextUtils.isEmpty(tvHomeLoan.text)) {
            tvHomeLoan.setText("0")
        }
        if (TextUtils.isEmpty(tvHomeRent.text)) {
            tvHomeRent.setText("0")
        }
        if (TextUtils.isEmpty(tvHelpOld.text)) {
            tvHelpOld.setText("0")
        }
        if (TextUtils.isEmpty(tvAttendance.text)) {
            tvAttendance.setText("0")
        }
        if (TextUtils.isEmpty(tvOthersFee.text)) {
            tvOthersFee.setText("0")
        }


        if (TextUtils.isEmpty(tvBase.text)) {
            ToastUtils.show("请输入基本工资")
            return
        }
        if (TextUtils.isEmpty(tvReservedFundsRate.text)) {
            ToastUtils.show("请输入公积金基数")
            return
        }

        if (TextUtils.isEmpty(tvPensionRate.text)) {
            ToastUtils.show("请输入养老金基数")
            return
        }
        if (TextUtils.isEmpty(tvMedicalRate.text)) {
            ToastUtils.show("请输入医疗保险基数")
            return
        }
        if (TextUtils.isEmpty(tvLossJobRate.text)) {
            ToastUtils.show("请输入失业保险基数")
            return
        }


        //应发  工资
        val salary = tvBase.text.toString().toDouble() +
                tvJobSalary.text.toString().toDouble() +
                tvAddSalary.text.toString().toDouble() +
                tvPerformSalary.text.toString().toDouble() +
                tvBonus.text.toString().toDouble() +
                tvWelfare.text.toString().toDouble()
        //社保 公积金 扣除部分
        val insure = tvReservedFunds.text.toString().toDouble() +
                tvPension.text.toString().toDouble() +
                tvMedical.text.toString().toDouble() +
                tvLossJob.text.toString().toDouble()

        //专项扣除部分
        val special = tvChildEdu.text.toString().toDouble() +
                tvContinueEdu.text.toString().toDouble() +
                tvBigDisease.text.toString().toDouble() +
                tvHomeLoan.text.toString().toDouble() +
                tvHomeRent.text.toString().toDouble() +
                tvHelpOld.text.toString().toDouble()
        //其他扣除
        val other = tvAttendance.text.toString().toDouble() +
                tvOthersFee.text.toString().toDouble()

        var rateBean = RatePersonalUtil.getPersonalRat(RatePersonalUtil.RateBean(salary, insure, special, 1, 5000.0, other, 0.0, 0.0))
        tvPersonalRate.setText(DecimalFormat("0.00").format(rateBean.personalRate))

        addSalary(rateBean)

    }


    private fun addSalary(rateBean: RatePersonalUtil.RateBean) {
        var salaryBean = SalaryBean(
                tvAddSalary.text.toString().toBigDecimal(),
                tvAttendance.text.toString().toBigDecimal(),
                tvBase.text.toString().toBigDecimal(),
                tvBigDisease.text.toString().toBigDecimal(),
                tvBonus.text.toString().toBigDecimal(),
                tvChildEdu.text.toString().toBigDecimal(),
                tvContinueEdu.text.toString().toBigDecimal(),
                Date(),
                tvHelpOld.text.toString().toBigDecimal(),
                tvHomeLoan.text.toString().toBigDecimal(),
                tvHomeRent.text.toString().toBigDecimal(),
                tvLossJob.text.toString().toBigDecimal(),
                tvLossJobRate.text.toString().toBigDecimal(),
                tvMedical.text.toString().toBigDecimal(),
                tvMedicalRate.text.toString().toBigDecimal(),
                tvPension.text.toString().toBigDecimal(),
                tvPensionRate.text.toString().toBigDecimal(),
                tvJobSalary.text.toString().toBigDecimal(),
                tvOthersFee.text.toString().toBigDecimal(),
                tvPerformSalary.text.toString().toBigDecimal(),
                tvPersonalRate.text.toString().toBigDecimal(),
                rateBean.reallySalary.toString().toBigDecimal(),
                tvReservedFunds.text.toString().toBigDecimal(),
                tvReservedFundsRate.text.toString().toBigDecimal(),
                salaryUserBean?.id!!,
                salaryUserBean?.name!!,
                tvWelfare.text.toString().toBigDecimal(),
                salaryUserBean?.state!!
        )

        var toJson = Gson().toJson(salaryBean)
        var create = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), toJson)
        var salary = RetrofitManager.retrofitManager.getRetrofit()
                .create(ApiService::class.java).addSalary(create)
        HttpRxObservable.getObservable(salary, this).subscribe(object : HttpRxObserver<SalaryBean>() {
            override fun onStart(d: Disposable) {
                L.d("start")
            }

            override fun onError(e: ApiException) {
                ToastUtils.show(e.msg)
            }

            override fun onSuccess(response: SalaryBean) {
                ToastUtils.show("${response.sName}  添加成功")
                finish()
            }
        })
    }
}
