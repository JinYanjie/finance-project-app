package com.kangce.finance.ui.entry_certificate

import android.app.Activity
import android.content.Intent
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.hjq.toast.ToastUtils
import com.kangce.finance.R
import com.kangce.finance.base.BaseActivity
import com.kangce.finance.bean.CertificateBean
import com.kangce.finance.bean.DepartmentBean
import com.kangce.finance.bean.StaffBean
import com.kangce.finance.http.exceptition.ApiException
import com.kangce.finance.http.observer.HttpRxObservable
import com.kangce.finance.http.observer.HttpRxObserver
import com.kangce.finance.http.ohkttp.RetrofitManager
import com.kangce.finance.http.service.ApiService
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.acticity_certificate.*
import kotlinx.android.synthetic.main.title.*

/**
 * 凭证字Activity
 */
class CertificationActivity : BaseActivity() {
    companion object {
        public const val requestCode = 101
        fun start(context: Activity) {
            context.startActivityForResult(Intent(context, CertificationActivity::class.java), requestCode)
        }
    }

    public var adpter: Adapter? = null
    override fun getLayoutId(): Int {
        return R.layout.acticity_certificate
    }

    override fun initView() {
        super.initView()
        tvTitle.text = "凭证字"
        back.setOnClickListener { finish() }
        imgRight.visibility = View.VISIBLE
        imgRight.setImageResource(R.drawable.add)
        imgRight.setOnClickListener {}
        adpter = Adapter()
        rvCertification.layoutManager = LinearLayoutManager(this)
        rvCertification.adapter = adpter
        getCertificate()

        adpter?.setOnItemClickListener { adapter, view, position ->
            var certBean = adapter.data[position] as CertificateBean
            var intent = Intent().putExtra("CertificateBean", certBean)
            setResult(200, intent)

            finish()
        }
    }

    private fun getCertificate() {
        var word = RetrofitManager.retrofitManager.getRetrofit().create(ApiService::class.java).getWord()
        HttpRxObservable.getObservable(word, this).subscribe(object : HttpRxObserver<List<CertificateBean>>() {
            override fun onStart(d: Disposable) {
            }

            override fun onError(e: ApiException) {
                ToastUtils.show(e.msg)
            }

            override fun onSuccess(response: List<CertificateBean>) {
                adpter?.setNewData(response)
            }
        })
    }

    class Adapter : BaseQuickAdapter<CertificateBean, BaseViewHolder> {
        constructor() : super(R.layout.item_certificate)

        override fun convert(helper: BaseViewHolder?, item: CertificateBean?) {
            if (item != null) {
                helper?.setText(R.id.tv_certificate, item.credentcontent)
                helper?.setText(R.id.tvDescription, item.printtitle)
            }
        }
    }
}
