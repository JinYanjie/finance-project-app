package com.kangce.finance

import android.content.Context
import android.content.Intent
import com.kangce.finance.base.BaseActivity
import com.kangce.finance.ui.nav.NavFragment
import com.kangce.finance.ui.nav.NavigationButton
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
