package com.kangce.finance.base.BaseList

import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.kangce.finance.base.BaseActivity
import com.kangce.finance.widget.MEmptyView
import com.kangce.finance.widget.MLoadMoreView

abstract class BaseListActivity<T> : BaseActivity(), RecyclerViewLoadListListener<T>, SwipeRefreshLayout.OnRefreshListener,
        BaseQuickAdapter.RequestLoadMoreListener, BaseQuickAdapter.OnItemClickListener {

    protected var PAGE_SIZE = 20
    protected var mPageNum = 1
    private var isRefresh = true

    private lateinit var mAdapter: BaseQuickAdapter<T, BaseViewHolder>
    protected abstract fun bindAdapter(): BaseQuickAdapter<T, BaseViewHolder>


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
        mAdapter = bindAdapter()

        getRecyclerView()!!.adapter = mAdapter
        mAdapter.onItemClickListener = this
        // 加载更多
        if (isOpenLoadMore()) {
            mAdapter.setOnLoadMoreListener(this, getRecyclerView())
            // 设置加载状态布局
            mAdapter.setLoadMoreView(MLoadMoreView())
        }

        if (isOpenEmptyView()) {
            // 设置空白布局
            setMyEmptyView()
        }

    }

    open fun setMyEmptyView() {
        mAdapter.emptyView = MEmptyView(this).view
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
        mAdapter.setNewData(null)
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
        mAdapter.setEnableLoadMore(true)
        val size = data?.size ?: 0

        //page为1 就刷新列表
        if (mPageNum == 1) {
            mAdapter.setNewData(data)
            isRefresh = false
        } else {
            if (size > 0) {
                mAdapter.addData(data!!)
            }
        }


        if (size < PAGE_SIZE) {
            // 没有更多了
            mAdapter.loadMoreEnd()
        } else {
            // 本次加载更多完成
            mAdapter.loadMoreComplete()
        }
    }

    override fun onLoadError() {
        // 请求数据失败
        if (getSwipeRefreshLayout() != null) {
            getSwipeRefreshLayout()!!.isRefreshing = false
        }

        mAdapter.setEnableLoadMore(true)
        mAdapter.loadMoreFail()

    }

    override fun onRefresh() {
        if (isOpenLoadMore()) {
            mAdapter.setEnableLoadMore(false)
        }
        isRefresh = true
        mPageNum = 1
        loadListData()
    }

    override fun onLoadMoreRequested() {
        mPageNum++
        mAdapter.setEnableLoadMore(true)
        isRefresh = false
        loadListData()
    }

}