package com.kangce.finance.ui.sheet

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.SyncStateContract
import android.support.v4.app.Fragment
import android.view.View
import com.kangce.finance.R
import com.kangce.finance.base.BaseActivity
import com.kangce.finance.utils.DisplayUtil
import kotlinx.android.synthetic.main.activity_balance_sheet.*
import kotlinx.android.synthetic.main.title.*

class BalanceSheetActivity : BaseActivity() {
    companion object {
        fun start(context: Context?) {
            context?.startActivity(Intent(context, BalanceSheetActivity::class.java))
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_balance_sheet
    }

    var fragmentList: List<Fragment>? = null
    private val titleList = listOf("流动资产", "流动负债")


    override fun initView() {
        super.initView()
        back.setOnClickListener { finish() }
        tvTitle.text = "资产负债表"
        imgRight.visibility = View.GONE

        initFragment()
        setViewPager()
        DisplayUtil.setIndicator(tab_dc, 40, 40)

    }

    private fun setViewPager() {
        var tabAdapter = TabAdapter(supportFragmentManager, fragmentList, titleList)
        vp_dc.adapter = tabAdapter
        tab_dc.setupWithViewPager(vp_dc)
        vp_dc.offscreenPageLimit = 2
        vp_dc.currentItem = 0
    }

    private fun initFragment() {
        val fragment1 = BalanceSheetFragment()
        val fragment2 = BalanceSheetFragment()

        var bundle1 = Bundle()
        bundle1.putInt("bundle", 0)
        fragment1.arguments = bundle1

        var bundle2 = Bundle()
        bundle2.putInt("bundle", 1)
        fragment2.arguments = bundle2

        fragmentList = listOf(fragment1, fragment2)
    }
}