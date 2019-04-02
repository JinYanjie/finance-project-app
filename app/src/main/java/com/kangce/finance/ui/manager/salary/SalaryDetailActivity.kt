package com.kangce.finance.ui.manager.salary

import android.content.Context
import android.content.Intent
import com.kangce.finance.base.BaseActivity
import com.kangce.finance.R

class SalaryDetailActivity : BaseActivity() {
    override fun getLayoutId(): Int {
        return R.layout.activity_detail_salary
    }

    companion object {
        fun start(context: Context, sId: Int) {
            val intent = Intent(context, SalaryDetailActivity::class.java)
            intent.putExtra("sId", sId)
            context.startActivity(intent)
        }
    }
}