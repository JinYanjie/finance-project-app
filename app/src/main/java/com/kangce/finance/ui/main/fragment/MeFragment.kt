package com.kangce.finance.ui.main.fragment

import android.os.Bundle
import android.view.View
import com.kangce.finance.R
import com.kangce.finance.R.id.tv_login
import com.kangce.finance.R.id.tv_logout
import com.kangce.finance.base.BaseFragment
import com.kangce.finance.ui.auth.LoginActivity
import com.kangce.finance.utils.UserCacheHelper
import kotlinx.android.synthetic.main.fragment_me_layout.*

class MeFragment : BaseFragment() {
    override fun getLayoutId(): Int {
        return R.layout.fragment_me_layout
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tv_login.setOnClickListener {
            LoginActivity.start(context!!)
        }

        tv_logout.setOnClickListener {
            UserCacheHelper.clear()
            showView()
        }

    }

    override fun onResume() {
        super.onResume()

        showView()

    }

    private fun showView() {
        val entity = UserCacheHelper.getEntity()

        if (entity == null) {
            tv_login.visibility = View.VISIBLE
            tv_logout.visibility = View.GONE
            ll_userContent.visibility = View.GONE
            img_userHead.visibility = View.GONE

        } else {
            val user = entity.user
            tv_login.visibility = View.GONE
            tv_logout.visibility = View.VISIBLE
            ll_userContent.visibility = View.VISIBLE
            img_userHead.visibility = View.VISIBLE

            tv_userName.text = "用户名: ${user.username}"
            tv_userPhone.text = "手机: ${user.phone}"
            tv_userMail.text = "邮箱: ${user.email}"
            tv_userLevel.text = "权限等级: ${
                when (user.level) {
                    1 -> "普通用户"
                    2 -> "管理员"
                    3 -> "高级管理员"
                    else -> ""
                }
            }"
        }
    }

}