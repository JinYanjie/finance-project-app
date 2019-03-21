package com.kangce.finance.ui.manager.userlevel

import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.RecyclerView
import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.kangce.finance.base.BaseActivity
import com.kangce.finance.base.BaseList.BaseListActivity
import com.kangce.finance.bean.UserEntity
import com.kangce.finance.choumou.R

import kotlinx.android.synthetic.main.activity_user_levle_setting.*

class UserLevelActivity:BaseListActivity<UserEntity>() {

    override fun getLayoutId(): Int = R.layout.activity_user_levle_setting




    override fun bindAdapter(): BaseQuickAdapter<UserEntity, BaseViewHolder> {
        return UserLevelAdapter()
    }

    override fun getRecyclerView(): RecyclerView? = recycler

    override fun getSwipeRefreshLayout(): SwipeRefreshLayout? = refresh

    override fun loadListData() {
    }

    override fun onItemClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
    }


}