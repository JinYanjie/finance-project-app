package com.kangce.finance.ui.main.fragment

import android.os.Bundle
import android.view.View
import com.kangce.finance.base.BaseFragment
import com.kangce.finance.R
import com.kangce.finance.ui.sheet.*
import kotlinx.android.synthetic.main.fragment_form_layout.*
import kotlinx.android.synthetic.main.title.*

class FormFragment : BaseFragment(), View.OnClickListener {

    override fun getLayoutId(): Int {
        return R.layout.fragment_form_layout
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        back.visibility = View.GONE
        imgRight.visibility = View.GONE
        tvTitle.text = "报表"
        sheet1.setOnClickListener(this)
        sheet2.setOnClickListener(this)
        sheet3.setOnClickListener(this)
        sheet4.setOnClickListener(this)
        sheet5.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.sheet1 -> {
                BalanceSheetActivity.start(context)
            }
            R.id.sheet2 -> {
                IncomeSheetActivity.start(context)
            }
            R.id.sheet3 -> {
                CashFlowSheetActivity.start(context)
            }
            R.id.sheet4 -> {
                WaitActivity.start(context)
            }
            R.id.sheet5 -> {
                AllocateActivity.start(context)
            }
        }
    }

}