package com.kangce.finance.ui.main.fragment

import android.os.Bundle
import android.view.View
import com.kangce.finance.base.BaseFragment
import com.kangce.finance.choumou.R
import com.kangce.finance.ui.auth.LoginActivity
import kotlinx.android.synthetic.main.fragment_me_layout.*

class MeFragment:BaseFragment() {
    override fun getLayoutId(): Int {
        return R.layout.fragment_me_layout
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tv_login.setOnClickListener{
            LoginActivity.start(context!!)
        }
    }

}