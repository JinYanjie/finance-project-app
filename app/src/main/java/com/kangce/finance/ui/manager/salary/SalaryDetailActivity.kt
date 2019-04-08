package com.kangce.finance.ui.manager.salary

import android.content.Context
import android.content.Intent
import android.view.View
import com.hjq.toast.ToastUtils
import com.kangce.finance.base.BaseActivity
import com.kangce.finance.R
import com.kangce.finance.bean.SalaryBean
import com.kangce.finance.bean.SalaryUserBean
import com.kangce.finance.http.exceptition.ApiException
import com.kangce.finance.http.observer.HttpRxObservable
import com.kangce.finance.http.observer.HttpRxObserver
import com.kangce.finance.http.ohkttp.RetrofitManager
import com.kangce.finance.http.service.ApiService
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_detail_salary.*
import kotlinx.android.synthetic.main.title.*
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class SalaryDetailActivity : BaseActivity() {
    override fun getLayoutId(): Int {
        return R.layout.activity_detail_salary
    }

    var sId: Int = 0

    companion object {
        fun start(context: Context, sId: Int) {
            val intent = Intent(context, SalaryDetailActivity::class.java)
            intent.putExtra("sId", sId)
            context.startActivity(intent)
        }
    }

    override fun initView() {
        super.initView()
        tvTitle.text = "工资详情"
        back.setOnClickListener { finish() }
        imgRight.visibility = View.GONE
        sId = intent.getIntExtra("sId", 0)
        getData(sId)

    }

    private fun getData(sId: Int) {
        var staff = RetrofitManager.retrofitManager.getRetrofit()
                .create(ApiService::class.java).getStaffSalary(sId)
        HttpRxObservable.getObservable(staff, this).subscribe(object : HttpRxObserver<SalaryBean>() {
            override fun onStart(d: Disposable) {
            }

            override fun onError(e: ApiException) {
                ToastUtils.show(e.msg)
            }

            override fun onSuccess(response: SalaryBean) {
                with(response) {
                    tvName.setText(sName)
                    tvSId.setText(sid.toString())
                    tvState.setText(if (state == 1) "在职" else "离职")
                    tvDate.setText(SimpleDateFormat("yy-MM-dd", Locale.CHINA).format(date.time))
                    tvBase.setText(baseSalary.toString())
                    tvJobSalary.setText(jobSalary.toString())
                    tvAddSalary.setText(addSalary.toString())
                    tvPerformSalary.setText(performSalary.toString())
                    tvBonus.setText(bonus.toString())
                    tvWelfare.setText(welfare.toString())
                    tvReservedFundsRate.setText(reservedFundsBase.toString())
                    tvReservedFunds.setText(reservedFunds.toString())
                    tvPensionRate.setText(insurePensionBase.toString())
                    tvPension.setText(insurePension.toString())
                    tvMedicalRate.setText(insureMedicineBase.toString())
                    tvMedical.setText(insureMedicine.toString())
                    tvLossJobRate.setText(insureJobBase.toString())
                    tvLossJob.setText(insureJob.toString())
                    tvChildEdu.setText(childEdu.toString())
                    tvContinueEdu.setText(continueEdu.toString())
                    tvBigDisease.setText(bigDisease.toString())
                    tvHomeLoan.setText(homeLoan.toString())
                    tvHomeRent.setText(homeRent.toString())
                    tvHelpOld.setText(helpOld.toString())
                    tvAttendance.setText(attandance.toString())
                    tvOthersFee.setText(otherFee.toString())
                    tvPersonalRate.setText(pTax.toString())
                    tvReallySalary.setText(reallySalary.toString())
                }
            }

        })
    }
}