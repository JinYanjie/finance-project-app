package com.kangce.finance.ui.entry_certificate

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.text.TextUtils
import android.view.View
import com.bigkoo.pickerview.builder.TimePickerBuilder
import com.bigkoo.pickerview.listener.OnTimeSelectListener
import com.bigkoo.pickerview.view.TimePickerView
import com.hjq.toast.ToastUtils
import com.kangce.finance.R
import com.kangce.finance.base.BaseActivity
import com.kangce.finance.bean.BaseCourseBean
import com.kangce.finance.bean.CertificateBean
import com.kangce.finance.bean.CertificateManagerBean
import com.kangce.finance.http.exceptition.ApiException
import com.kangce.finance.http.observer.HttpRxObservable
import com.kangce.finance.http.observer.HttpRxObserver
import com.kangce.finance.http.ohkttp.RetrofitManager
import com.kangce.finance.http.service.ApiService
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.acticity_entry_certificate.*
import kotlinx.android.synthetic.main.title.*
import java.text.SimpleDateFormat
import java.util.*

/**
 * 录入凭证
 */
class EntryCertificateActivity : BaseActivity(), View.OnClickListener {
    private var pvTime: TimePickerView? = null
    val format by lazy { SimpleDateFormat("yyyy-MM-dd") }
    var beanBorrow = CertificateManagerBean(null, null, null, null, null,null, null, null, null, 2, null, 1)
    var beanLoan = CertificateManagerBean(null, null, null, null, null,null, null, null, null, 2, null, 2)

    companion object {
        fun start(context: Context?) {
            context?.startActivity(Intent(context, EntryCertificateActivity::class.java))
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.acticity_entry_certificate
    }


    override fun initView() {
        super.initView()
        tvTitle.text = "录入凭证"
        imgRight.visibility = View.GONE
        back.setOnClickListener { finish() }
        ll_certificate.setOnClickListener(this)
        llBaseCourse.setOnClickListener(this)
        llBaseCourseCredit.setOnClickListener(this)
        llDate.setOnClickListener(this)
        tv_save.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.ll_certificate -> {
                CertificationActivity.start(this)
            }
            //借方 会计科目
            R.id.llBaseCourse -> {
                BaseCourseActivity.start(this, 201)

            }
            //贷方 会计科目
            R.id.llBaseCourseCredit -> {
                BaseCourseActivity.start(this, 202)
            }
            R.id.llDate -> {
                initTimePicker()
                pvTime?.show()
            }
            R.id.tv_save -> {
                save()
            }
        }
    }

    private fun save() {
        var mutableListOf = mutableListOf<CertificateManagerBean>()
        //虽然借方、贷方 有多种，但app这里 只做了  一个借方、一个贷方的情况
        if (TextUtils.isEmpty(tvBorrowSum.text.toString()) ||
                TextUtils.isEmpty(tvLoanSum.text.toString()) ||
                TextUtils.isEmpty(tvBorrowMoney.text.toString()) ||
                TextUtils.isEmpty(tvLoanMoney.text.toString()) ||
                TextUtils.isEmpty(tv_date.text.toString())) {
            ToastUtils.show("请填写相关信息")
            return
        }
        if (TextUtils.equals("请选择凭证字", tv_certificate.text.toString())) {
            ToastUtils.show("请选择关键字")
            return
        }
        if (TextUtils.equals("请选择会计科目", tv_BaseCourse.text.toString())) {
            ToastUtils.show("请选择借方会计科目")
            return
        }
        if (TextUtils.equals("请选择会计科目", tv_BaseCourseCredit.text.toString())) {
            ToastUtils.show("请选择贷方会计科目")
            return
        }


        beanBorrow.summary = tvBorrowSum.text.toString()
        beanLoan.summary = tvLoanSum.text.toString()

        beanBorrow.inMoney = tvBorrowMoney.text.toString().toBigDecimal()
        beanBorrow.outMoney = "0".toBigDecimal()

        beanLoan.inMoney = "0".toBigDecimal()
        beanLoan.outMoney = tvLoanMoney.text.toString().toBigDecimal()

        if (beanBorrow.inMoney != beanLoan.outMoney) {
            ToastUtils.show("借方、贷方金额不相等")
            return
        }

        mutableListOf.add(beanBorrow)
        mutableListOf.add(beanLoan)


        var baseCourse = RetrofitManager.retrofitManager.getRetrofit().create(ApiService::class.java).addCertificate(mutableListOf)

        HttpRxObservable.getObservable(baseCourse, this).subscribe(object : HttpRxObserver<Any>() {
            override fun onStart(d: Disposable) {
            }

            override fun onError(e: ApiException) {
                ToastUtils.show(e.msg)
            }

            override fun onSuccess(response: Any) {
                ToastUtils.show(response.toString())
                beanBorrow = CertificateManagerBean(null, null, null, null, null,null, null, null, null, 2, null, 1)
                beanLoan = CertificateManagerBean(null, null, null, null, null,null, null, null, null, 2, null, 1)
                tv_certificate.text = "请选择凭证字"
                tv_date.text = "请选择日期"
                tvBorrowSum.setText("")
                tv_BaseCourse.text = "请选择会计科目"
                tvBorrowMoney.setText("")
                tvLoanSum.setText("")
                tv_BaseCourseCredit.text = "请选择会计科目"
                tvLoanMoney.setText("")
            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == 200) {
            when (requestCode) {
                101 -> {
                    var certificateBean = data?.getSerializableExtra("CertificateBean") as CertificateBean
                    tv_certificate.text = certificateBean.credentcontent
                    //凭证字
                    beanBorrow.certificateNum = certificateBean.credentcontent
                    beanLoan.certificateNum = certificateBean.credentcontent

                }
                201 -> {
                    var baseCourseBean = data?.getSerializableExtra("BaseCourseBean") as BaseCourseBean
                    tv_BaseCourse.text = baseCourseBean.cname
                    beanBorrow.cname = baseCourseBean.cname
                    beanBorrow.cnum = baseCourseBean.num

                }
                202 -> {
                    var baseCourseBean = data?.getSerializableExtra("BaseCourseBean") as BaseCourseBean
                    tv_BaseCourseCredit.text = baseCourseBean.cname
                    beanLoan.cname = baseCourseBean.cname
                    beanLoan.cnum = baseCourseBean.num
                }
            }
        }
    }


    /**
     * 日期选择器
     */
    private fun initTimePicker() {
        //控制时间范围(如果不设置范围，则使用默认时间1900-2100年，此段代码可注释)
        //因为系统Calendar的月份是从0-11的,所以如果是调用Calendar的set方法来设置时间,月份的范围也要是从0-11
        val selectedDate = Calendar.getInstance()

        val startDate = Calendar.getInstance()
        startDate.set(2016, 0, 1)

        val endDate = Calendar.getInstance()
        endDate.set(2100, 12, 31)
        //时间选择器
        pvTime = TimePickerBuilder(this, object : OnTimeSelectListener {
            override fun onTimeSelect(date: Date?, v: View?) {
                // 这里回调过来的v,就是show()方法里面所添加的 View 参数，如果show的时候没有添加参数，v则为null
                val dateText = format.format(date)

                beanBorrow.date = dateText
                beanLoan.date = dateText

                tv_date.text = dateText
                tv_date.setTag(date)
            }

        })
                .setType(booleanArrayOf(true, true, true, false, false, false))
                .setLabel("", "", "", "", "", "") //设置空字符串以隐藏单位提示   hide label
                .setDividerColor(Color.DKGRAY)
                .setContentTextSize(20)
                .setDate(selectedDate)
                .setRangDate(startDate, endDate)
                .setOutSideColor(0x00000000)
                .setOutSideCancelable(false)
                .build()

        pvTime?.setKeyBackCancelable(false)//系统返回键监听屏蔽掉
    }

}