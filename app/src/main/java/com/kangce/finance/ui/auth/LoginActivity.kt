package com.kangce.finance.ui.auth

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import com.kangce.finance.base.BaseActivity
import com.kangce.finance.bean.LoginSuccess
import com.kangce.finance.choumou.R
import com.kangce.finance.utils.T
import com.kangce.finance.utils.UserCacheHelper
import kotlinx.android.synthetic.main.activity_register.*

class LoginActivity :BaseActivity(),LoginContract.View, View.OnClickListener {


    val loginPresenter by lazy { LoginPresenter(this,this) }

    override fun getLayoutId(): Int = R.layout.activity_register


    override fun initView() {
        super.initView()

        btn_submit.setOnClickListener(this)


    }


    override fun onClick(view: View?) {
        when(view?.id){
            R.id.btn_submit->{
                val userPhone = edit_phone.text.toString().trim()
                val password = edit_password.text.toString().trim()
                val repeatPassword = edit_repeat.text.toString().trim()

                if(TextUtils.isEmpty(userPhone)){
                    T.showShort("请输入手机号")
                    return
                }

                if (TextUtils.isEmpty(password)){
                    T.showShort("请输入密码")
                    return
                }

                if (TextUtils.isEmpty(repeatPassword) || repeatPassword.equals(password)){
                    T.showShort("二次密码输入不一致")
                    return
                }

                loginPresenter.loginStart(userPhone,password)


            }
        }
    }


    override fun onRegisterSuccess(success: LoginSuccess) {
        UserCacheHelper.update(success)
    }





}