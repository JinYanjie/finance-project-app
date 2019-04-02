package com.example.textphtp

import android.app.Application
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
    }


}
