package com.kangce.finance.ui.sheet

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

class TabAdapter(fm: FragmentManager, private val fragmentList: List<Fragment>?,
                 private val tabs: List<String>) : FragmentPagerAdapter(fm) {


    override fun getItem(position: Int): Fragment? {
        return if (fragmentList == null || fragmentList.size < position) {
            null
        } else {
            fragmentList[position]
        }
    }

    override fun getCount(): Int {
        return fragmentList?.size ?: 0
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return tabs[position]
    }
}
