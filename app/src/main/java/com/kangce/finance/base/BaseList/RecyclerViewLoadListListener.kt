package com.kangce.finance.base.BaseList

import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder

interface RecyclerViewLoadListListener<DATA> {

    fun getRecyclerView(): RecyclerView?

    fun getSwipeRefreshLayout(): SwipeRefreshLayout?

    fun getLayoutManager(): RecyclerView.LayoutManager?

    fun isOpenAuthRefresh(): Boolean

    fun isOpenLoadMore(): Boolean

    fun initRecyclerRefreshAdapter()

    fun startRefresh()

    fun loadListData()

    fun onSetLoadData(data: List<DATA>?)

    fun onLoadError()
}
