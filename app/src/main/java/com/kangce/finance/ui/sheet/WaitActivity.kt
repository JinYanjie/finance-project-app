package com.kangce.finance.ui.sheet

import android.content.Context
import android.content.Intent
import android.view.View
import com.kangce.finance.R
import com.kangce.finance.base.BaseActivity
import kotlinx.android.synthetic.main.title.*

class WaitActivity : BaseActivity() {
    override fun getLayoutId(): Int {
        return R.layout.activity_wait
    }

    companion object {
        fun start(context: Context?) {
            context?.startActivity(Intent(context, WaitActivity::class.java))
        }
    }

    override fun initView() {
        super.initView()
        back.setOnClickListener { finish() }
        imgRight.visibility = View.GONE
        tvTitle.text = "利润表"
    }
}