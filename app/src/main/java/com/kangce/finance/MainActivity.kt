package com.kangce.finance

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.kangce.finance.base.BaseActivity


import com.kangce.finance.bean.DepartmentBean
import com.kangce.finance.choumou.R
import com.kangce.finance.choumou.http.exceptition.ApiException
import com.kangce.finance.choumou.http.observer.HttpRxObservable
import com.kangce.finance.choumou.http.observer.HttpRxObserver
import com.kangce.finance.choumou.http.ohkttp.RetrofitManager
import com.kangce.finance.choumou.http.service.ApiService
import com.kangce.finance.ui.nav.NavFragment
import com.kangce.finance.ui.nav.NavigationButton
import com.kangce.finance.utils.L
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity
import io.reactivex.disposables.Disposable

class MainActivity : BaseActivity(), NavFragment.OnNavigationReselectListener {


    private var navFragment: NavFragment? = null
    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, MainActivity::class.java))
        }
    }

    override fun initView() {
        super.initView()
        val fragmentManager = supportFragmentManager
        navFragment = fragmentManager.findFragmentById(R.id.fragment_nav) as NavFragment?
        navFragment?.stup(this, fragmentManager, R.id.main_container, this)
    }




    override fun onReselect(navigationButton: NavigationButton) {
    }

    override fun whichNavigationButtonShow(navigationButton: NavigationButton) {
    }
}
