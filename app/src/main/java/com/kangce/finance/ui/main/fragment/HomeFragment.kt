package com.kangce.finance.ui.main.fragment

import android.os.Bundle
import android.view.View
import com.kangce.finance.base.BaseFragment
import com.kangce.finance.R
import com.kangce.finance.ui.fixedassets.FixedAssetsActivity
import kotlinx.android.synthetic.main.fragment_home_layout.*

class HomeFragment:BaseFragment(), View.OnClickListener {


    override fun getLayoutId(): Int {
        return R.layout.fragment_home_layout
    }


    override fun initView(view: View) {

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        iv_fixedAssets.setOnClickListener(this)

    }


    override fun onClick(v: View?) {
        when(v?.id){
            R.id.iv_fixedAssets->{
                FixedAssetsActivity.start(context)
            }
        }
    }

}