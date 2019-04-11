package com.kangce.finance.ui.entry_certificate

import android.content.Context
import android.content.Intent
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.hjq.toast.ToastUtils
import com.kangce.finance.R
import com.kangce.finance.base.BaseActivity
import com.kangce.finance.bean.CertificateListBean
import com.kangce.finance.bean.CertificateManagerBean
import com.kangce.finance.bean.UserEntity
import com.kangce.finance.http.exceptition.ApiException
import com.kangce.finance.http.observer.HttpRxObservable
import com.kangce.finance.http.observer.HttpRxObserver
import com.kangce.finance.http.ohkttp.RetrofitManager
import com.kangce.finance.http.service.ApiService
import com.kangce.finance.utils.UserCacheHelper
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_certificate_detail.*
import kotlinx.android.synthetic.main.title.*

class CertificateDetailActivity : BaseActivity() {
    val adapter: Adapter by lazy { Adapter() }
    override fun getLayoutId(): Int {
        return R.layout.activity_certificate_detail
    }

    companion object {
        fun start(context: Context, bean: CertificateListBean) {
            var intent = Intent(context, CertificateDetailActivity::class.java)
            intent.putExtra("CertificateListBean", bean)
            context.startActivity(intent)
        }
    }

    override fun initView() {
        super.initView()
        tvTitle.text = "凭证详情"
        var bean = intent.getSerializableExtra("CertificateListBean") as CertificateListBean


        if (bean.checkState == 2) {
            imgRight.visibility = View.VISIBLE
            tvCheckName.text = ""
            tvState.text = "未审核"
        } else if (bean.checkState == 1) {
            getCheckUserInfo(bean.checkUserId)
            imgRight.visibility = View.GONE
            tvState.text = "已审核"
        }
        back.setOnClickListener { finish() }
        imgRight.setImageResource(R.drawable.finish)
        imgRight.setOnClickListener {
            gotoCheck(bean)
        }

        rvCertificateDetail.layoutManager = LinearLayoutManager(this)
        rvCertificateDetail.adapter = adapter
        adapter.setNewData(bean.nameList)

    }

    private fun getCheckUserInfo(checkUserId: Int?) {
        if (checkUserId == null) {
            return
        }
        var user = RetrofitManager.retrofitManager.getRetrofit().create(ApiService::class.java).getUserById(checkUserId)
        HttpRxObservable.getObservable(user, this).subscribe(object : HttpRxObserver<UserEntity>() {
            override fun onStart(d: Disposable) {
            }

            override fun onError(e: ApiException) {
                ToastUtils.show(e.msg)
            }

            override fun onSuccess(response: UserEntity) {
                tvCheckName.text = response.username
            }
        })
    }

    private fun gotoCheck(bean: CertificateListBean) {
        var check = RetrofitManager.retrofitManager.getRetrofit().create(ApiService::class.java)
                .checkCertificate(bean.nameList[0].certificateNum!!, bean.nameList[0].currentNum!!
                        , UserCacheHelper.getEntity()?.user?.id!!, 1)
        HttpRxObservable.getObservable(check, this).subscribe(object : HttpRxObserver<Any>() {
            override fun onStart(d: Disposable) {
            }

            override fun onError(e: ApiException) {
                ToastUtils.show(e.msg)
            }

            override fun onSuccess(response: Any) {
                ToastUtils.show(response.toString())
            }
        })

    }

    class Adapter : BaseQuickAdapter<CertificateManagerBean, BaseViewHolder> {
        constructor() : super(R.layout.item_detail_certificate)

        override fun convert(helper: BaseViewHolder?, item: CertificateManagerBean?) {
            if (item != null) {
                with(item) {
                    helper?.setText(R.id.tvValue1, summary)
                    helper?.setText(R.id.tvValue2, "$cnum    $cname")
                    helper?.setText(R.id.tvValue3, "$inMoney")
                    helper?.setText(R.id.tvValue4, "$outMoney")
                }
            }
        }
    }
}