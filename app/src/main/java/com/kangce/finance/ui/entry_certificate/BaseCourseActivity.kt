package com.kangce.finance.ui.entry_certificate

import android.app.Activity
import android.content.Intent
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.hjq.toast.ToastUtils
import com.kangce.finance.R
import com.kangce.finance.base.BaseActivity
import com.kangce.finance.bean.BaseCourseBean
import com.kangce.finance.http.exceptition.ApiException
import com.kangce.finance.http.observer.HttpRxObservable
import com.kangce.finance.http.observer.HttpRxObserver
import com.kangce.finance.http.ohkttp.RetrofitManager
import com.kangce.finance.http.service.ApiService
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_base_course.*
import kotlinx.android.synthetic.main.item_basecourse.view.*
import kotlinx.android.synthetic.main.title.*

class BaseCourseActivity : BaseActivity(), View.OnClickListener {


    var type = 0

    companion object {
        fun start(context: Activity?, requestCode: Int) {
            context?.startActivityForResult(Intent(context, BaseCourseActivity::class.java), requestCode)
        }
    }

    public var adpter: Adapter? = null
    override fun getLayoutId(): Int {
        return R.layout.activity_base_course
    }

    override fun initView() {
        super.initView()
        tvTitle.text = "会计科目"
        back.setOnClickListener { finish() }
        imgRight.visibility = View.VISIBLE
        imgRight.setImageResource(R.drawable.add)
        imgRight.setOnClickListener {}

        adpter = Adapter()
        rvBaseCourse.layoutManager = LinearLayoutManager(this)
        rvBaseCourse.adapter = adpter
        adpter?.setOnItemClickListener { adapter, view, position ->
            var baseBean = adapter.data[position] as BaseCourseBean
            var intent = Intent().putExtra("BaseCourseBean", baseBean)
            setResult(200, intent)
            finish()
        }
        loadData(type)

        b1.setOnClickListener(this)
        b2.setOnClickListener(this)
        b3.setOnClickListener(this)
        b4.setOnClickListener(this)
        b5.setOnClickListener(this)
        b6.setOnClickListener(this)
        b7.setOnClickListener(this)
    }

    private fun loadData(mType: Int) {
        var baseCourse = RetrofitManager.retrofitManager.getRetrofit().create(ApiService::class.java).getBaseCourse(mType)
        HttpRxObservable.getObservable(baseCourse, this).subscribe(object : HttpRxObserver<List<BaseCourseBean>>() {
            override fun onStart(d: Disposable) {
            }

            override fun onError(e: ApiException) {
                ToastUtils.show(e.msg)
            }

            override fun onSuccess(response: List<BaseCourseBean>) {
                adpter?.setNewData(response)
            }
        })
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.b1 -> {
                type = 1
            }
            R.id.b2 -> {
                type = 2
            }
            R.id.b3 -> {
                type = 3
            }
            R.id.b4 -> {
                type = 4
            }
            R.id.b5 -> {
                type = 5
            }
            R.id.b6 -> {
                type = 6
            }
            R.id.b7 -> {
                type = 0
            }
        }
        loadData(type)
    }

    class Adapter : BaseQuickAdapter<BaseCourseBean, BaseViewHolder> {
        constructor() : super(R.layout.item_basecourse)

        override fun convert(helper: BaseViewHolder?, item: BaseCourseBean?) {
            if (item != null) {
                helper?.setText(R.id.tv_number, item.num)
                helper?.setText(R.id.tvDescription, item.cname)
            }
        }
    }


}