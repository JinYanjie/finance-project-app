package com.kangce.finance.ui.nav

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.view.View
import com.kangce.finance.base.BaseFragment
import com.kangce.finance.choumou.R
import com.kangce.finance.ui.main.fragment.FormFragment
import com.kangce.finance.ui.main.fragment.HomeFragment
import com.kangce.finance.ui.main.fragment.ManageFragment
import com.kangce.finance.ui.main.fragment.MeFragment
import kotlinx.android.synthetic.main.fragment_nav.*

class NavFragment : BaseFragment(), View.OnClickListener {
    private var prePosition = -1
    private var mFragmentManager: FragmentManager? = null
    private var mContainerId: Int = 0
    private var mContext: Context? = null
    private var mOnNavigationReselectListener: OnNavigationReselectListener? = null
    // 当前选中的item
    private var mCurrentNavButton: NavigationButton? = null

    override fun getLayoutId(): Int {
        return R.layout.fragment_nav
    }

    override fun initView(view: View) {
        super.initView(view)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        nav_item_home.init(R.drawable.tab_icon_home, R.string.nav_home, HomeFragment::class.java)
        nav_item_form.init(R.drawable.tab_icon_form, R.string.nav_form, FormFragment::class.java)
        nav_item_manage.init(R.drawable.tab_icon_manage, R.string.nav_manage, ManageFragment::class.java)
        nav_item_me.init(R.drawable.tab_icon_me, R.string.nav_me, MeFragment::class.java)

        nav_item_home.setOnClickListener(this)
        nav_item_form.setOnClickListener(this)
        nav_item_manage.setOnClickListener(this)
        nav_item_me.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.nav_item_home -> {
                prePosition = 0
            }
            R.id.nav_item_form -> {
                prePosition = 1
            }
            R.id.nav_item_manage -> {
                prePosition = 2
            }
            R.id.nav_item_me -> {
                prePosition = 3
            }
        }
        val nav = v as NavigationButton
        doSelect(nav)
    }

    fun stup(context: Context, fragmentManager: FragmentManager,
             contentId: Int, listener: OnNavigationReselectListener) {
        mContext = context
        mFragmentManager = fragmentManager
        mContainerId = contentId
        mOnNavigationReselectListener = listener

        clearOldFragment()
        doSelect(nav_item_home)
    }

    private fun doSelect(newNavButton: NavigationButton) {
        var oldNavButton: NavigationButton? = null
        if (mCurrentNavButton != null) {
            oldNavButton = mCurrentNavButton
            if (oldNavButton === newNavButton) {
                onReselect(oldNavButton)
                return
            }
            oldNavButton!!.isSelected = false
        }
        newNavButton.isSelected = true
        doTabChanged(oldNavButton, newNavButton)
        mCurrentNavButton = newNavButton
    }

    // 切换fragment
    private fun doTabChanged(oldNavButton: NavigationButton?, newNavButton: NavigationButton?) {
        val ft = mFragmentManager?.beginTransaction()
        if (oldNavButton != null) {
            if (oldNavButton.fragment != null) {
                ft?.hide(oldNavButton.fragment!!)
            }
        }
        if (newNavButton != null) {
            if (newNavButton.fragment == null) {
                val fragment = Fragment.instantiate(mContext, newNavButton.clx?.name, null)
                ft?.add(mContainerId, fragment, newNavButton.tag)
                newNavButton.fragment = fragment
            } else {
                ft?.show(newNavButton.fragment!!)
            }
        }
        mOnNavigationReselectListener?.whichNavigationButtonShow(newNavButton!!)
        ft?.commitAllowingStateLoss()
    }

    private fun onReselect(navigationButton: NavigationButton) {
        val listener = mOnNavigationReselectListener
        listener?.onReselect(navigationButton)
    }

    // 清除之前选中的item
    private fun clearOldFragment() {
        val transaction = mFragmentManager?.beginTransaction()
        val fragments = mFragmentManager?.fragments
        if (transaction == null || fragments == null || fragments.size == 0)
            return
        var doCommit = false
        for (fragment in fragments) {
            if (fragment != this && fragment != null) {
                transaction.remove(fragment)
                doCommit = true
            }
        }
        if (doCommit)
            transaction.commitNow()
    }

    // item重新选中的回调
    interface OnNavigationReselectListener {
        fun onReselect(navigationButton: NavigationButton)
        fun whichNavigationButtonShow(navigationButton: NavigationButton)
    }
}