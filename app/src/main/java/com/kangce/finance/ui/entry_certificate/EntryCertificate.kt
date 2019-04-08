package com.kangce.finance.ui.entry_certificate

import android.view.View
import com.kangce.finance.R
import com.kangce.finance.base.BaseActivity
import com.kangce.finance.ui.manager.staff.AddStaffActivity
import kotlinx.android.synthetic.main.title.*

/**
 * 录入凭证
 */
class EntryCertificate:BaseActivity() {
    override fun getLayoutId(): Int {
        return R.layout.acticity_entry_certificate
    }

    override fun initView() {
        super.initView()
        tvTitle.text = "录入凭证"
        imgRight.visibility = View.VISIBLE
        imgRight.setImageResource(R.drawable.add)
        imgRight.setOnClickListener {}
    }
}