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
import com.kangce.finance.http.exceptition.ApiException
import com.kangce.finance.http.observer.HttpRxObservable
import com.kangce.finance.http.observer.HttpRxObserver
import com.kangce.finance.http.ohkttp.RetrofitManager
import com.kangce.finance.http.service.ApiService
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_certificate_manager.*
import kotlinx.android.synthetic.main.fragment_manage_layout.*
import kotlinx.android.synthetic.main.title.*
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

/**
 * 凭证管理
 */
class CertificateManagerActivity : BaseActivity(), View.OnClickListener {
    var state = 0

    override fun getLayoutId(): Int {
        return R.layout.activity_certificate_manager
    }

    companion object {
        fun start(context: Context?) {
            context?.startActivity(Intent(context, CertificateManagerActivity::class.java))
        }
    }

    val adapter: Adapter by lazy { Adapter() }

    override fun initView() {
        super.initView()
        tvTitle.text = "凭证管理"
        back.setOnClickListener { finish() }
        imgRight.visibility = View.VISIBLE
        imgRight.setImageResource(R.drawable.add)
        imgRight.setOnClickListener {
            EntryCertificateActivity.start(this)
        }
        rvCertificate.layoutManager = LinearLayoutManager(this)
        rvCertificate.adapter = adapter
        getData(state)
        b1.setOnClickListener(this)
        b2.setOnClickListener(this)
        b3.setOnClickListener(this)
        adapter.setOnItemClickListener { adapter, view, position ->
            var certificateListBean = adapter.data[position] as CertificateListBean
            CertificateDetailActivity.start(this, certificateListBean)
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.b1 -> {
                state = 0
            }
            R.id.b2 -> {
                state = 1
            }
            R.id.b3 -> {
                state = 2
            }
        }
        getData(state)
    }

    private fun getData(state: Int) {
        var certificate = RetrofitManager.retrofitManager.getRetrofit().create(ApiService::class.java).getCertificate(state)
        HttpRxObservable.getObservable(certificate, this).subscribe(object : HttpRxObserver<List<CertificateListBean>>() {
            override fun onStart(d: Disposable) {
            }

            override fun onError(e: ApiException) {
                ToastUtils.show(e.msg)
            }

            override fun onSuccess(response: List<CertificateListBean>) {
                adapter.setNewData(response)
            }
        })
    }

    class Adapter : BaseQuickAdapter<CertificateListBean, BaseViewHolder> {
        constructor() : super(R.layout.item_certificate_manager)

        override fun convert(helper: BaseViewHolder?, item: CertificateListBean?) {
            if (item != null) {
                with(item) {
                    helper?.setText(R.id.tvName, name)
                    helper?.setText(R.id.tvDate, SimpleDateFormat("yyyy-MM-dd hh:MM").format(date))
                }
            }
        }

    }

}