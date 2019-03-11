package com.kangce.finance.base.BaseList

import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.kangce.finance.base.BaseActivity
import com.kangce.finance.widget.MEmptyView
import com.kangce.finance.widget.MLoadMoreView

abstract class BaseListActivity<T> : BaseActivity(), RecyclerViewLoadListListener<T>, SwipeRefreshLayout.OnRefreshListener,
        BaseQuickAdapter.RequestLoadMoreListener, BaseQuickAdapter.OnItemClickListener {

    protected var PAGE_SIZE = 20
    protected var mPageNum = 1
    private var isRefresh = true


    override fun initView() {
        super.initView()
        initRecyclerRefreshAdapter()
    }


    override fun initRecyclerRefreshAdapter() {
        if (getSwipeRefreshLayout() != null) {
            getSwipeRefreshLayout()!!.setOnRefreshListener(this)
        }

        if (getRecyclerView() != null) {
            if (getRecyclerView()!!.layoutManager == null) {
                getRecyclerView()!!.layoutManager = getLayoutManager()
            }
            setAdapter()
            // 自动刷新
            if (isOpenAuthRefresh()) {
                authRefresh()
            }
        }
    }

    // 加载成功设置adapter
    private fun setAdapter() {

        if (getAdapter() != null) {
            getRecyclerView()!!.adapter = getAdapter()
            getAdapter()!!.onItemClickListener = this
            // 加载更多
            if (isOpenLoadMore()) {
                getAdapter()!!.setOnLoadMoreListener(this, getRecyclerView())
                // 设置加载状态布局
                getAdapter()!!.setLoadMoreView(MLoadMoreView())
            }

            if (isOpenEmptyView()) {
                // 设置空白布局
                setMyEmptyView()
            }
        }
    }

    open fun setMyEmptyView() {
        getAdapter()!!.emptyView = MEmptyView(this).view
    }

    fun isOpenEmptyView(): Boolean {
        return true
    }

    fun authRefresh() {
        if (getSwipeRefreshLayout() != null) {
            getSwipeRefreshLayout()!!.post {
                getSwipeRefreshLayout()!!.isRefreshing = true
                isRefresh = true
                mPageNum = 1
                loadListData()
            }
        }
    }

    fun clearAdapterData() {
        getAdapter()?.setNewData(null)
    }


    override fun getLayoutManager(): RecyclerView.LayoutManager {
        return LinearLayoutManager(this)
    }

    override fun isOpenAuthRefresh(): Boolean {
        return true
    }

    override fun isOpenLoadMore(): Boolean {
        return true
    }

    override fun startRefresh() {
        authRefresh()
    }

    override fun onSetLoadData(data: List<T>?) {
        // 请求数据完成
        if (getSwipeRefreshLayout() == null) return
        getSwipeRefreshLayout()!!.isRefreshing = false
        getAdapter()!!.setEnableLoadMore(true)
        val size = data?.size ?: 0

        //page为1 就刷新列表
        if (mPageNum == 1) {
            getAdapter()!!.setNewData(data)
            isRefresh = false
        } else {
            if (size > 0) {
                getAdapter()!!.addData(data!!)
            }
        }

        if (size < PAGE_SIZE) {
            // 没有更多了
            getAdapter()!!.loadMoreEnd()
        } else {
            // 本次加载更多完成
            getAdapter()!!.loadMoreComplete()
        }
    }

    override fun onLoadError() {
        // 请求数据失败
        if (getSwipeRefreshLayout() != null) {
            getSwipeRefreshLayout()!!.isRefreshing = false
        }
        if (getAdapter() != null) {
            getAdapter()!!.setEnableLoadMore(true)
            getAdapter()!!.loadMoreFail()
        }
    }

    // ========================================================================================== //

    override fun onRefresh() {
        if (isOpenLoadMore()) {
            getAdapter()!!.setEnableLoadMore(false)
        }
        isRefresh = true
        mPageNum = 1
        loadListData()
    }

    override fun onLoadMoreRequested() {
        mPageNum++
        getAdapter()!!.setEnableLoadMore(true)
        isRefresh = false
        loadListData()
    }

}