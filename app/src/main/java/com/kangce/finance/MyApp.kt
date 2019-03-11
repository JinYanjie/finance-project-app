package com.kangce.finance

import android.app.Application

import com.hjq.toast.ToastUtils
import com.kangce.finance.utils.L
import com.kangce.finance.utils.MyHawkSpStorage
import com.orhanobut.hawk.Hawk
import kotlin.properties.Delegates


class MyApp : Application() {

    //单例化 利用系统自带的Delegates生成委托属性
    companion object {
        private var instance: MyApp by Delegates.notNull()
        fun instance() = instance
    }


    override fun onCreate() {
        super.onCreate()
        instance = this

        L.init(this)
        ToastUtils.init(this)
        //Hawk初始化
        initHawk()
    }

    fun initHawk(){
        Hawk.init(this)
                .setStorage(MyHawkSpStorage(this, Constant.SP_FILENAME_MAIN))
                .build()
    }

}
