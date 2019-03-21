package com.kangce.finance.ui.auth

import android.content.Context
import android.content.Intent
import android.text.TextUtils
import android.view.View
import com.kangce.finance.base.BaseActivity
import com.kangce.finance.bean.LoginSuccess
import com.kangce.finance.R
import com.kangce.finance.utils.T
import com.kangce.finance.utils.UserCacheHelper
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity :BaseActivity(), LoginContract.View, View.OnClickListener {



    val loginPresenter by lazy { LoginPresenter(this, this) }

    override fun getLayoutId(): Int = R.layout.activity_login




    companion object {
        fun start(context: Context) {
            val intent = Intent(context, LoginActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun initView() {
        super.initView()
        btn_submit.setOnClickListener(this)
        ll_jumpToCode.setOnClickListener(this)
    }



    override fun onClick(v: View?) {
        when(v?.id){
            R.id.btn_submit->{
                val userPhone = edit_phone.text.toString().trim()
                val password = edit_pwd.text.toString().trim()

                if (TextUtils.isEmpty(userPhone)) {
                    T.showShort("请输入手机号")
                    return
                }

                if (TextUtils.isEmpty(password)) {
                    T.showShort("请输入密码")
                    return
                }
                loginPresenter.loginStart(userPhone,password)
            }
            R.id.ll_jumpToCode->{
                RegisterActivity.start(this)
            }
        }
    }



    override fun onRegisterSuccess(success: LoginSuccess) {

    }

    override fun onLoginSuccess(success: LoginSuccess) {
        UserCacheHelper.update(success)
        finish()
    }
}