package com.kangce.finance.widget

import com.chad.library.adapter.base.loadmore.LoadMoreView
import com.kangce.finance.R

class MLoadMoreView : LoadMoreView() {

    override fun getLayoutId(): Int {
        return R.layout.layout_list_load_more
    }

    override fun getLoadingViewId(): Int {
        return R.id.load_more_loading_view
    }

    override fun getLoadFailViewId(): Int {
        return R.id.load_more_load_fail_view
    }

    /**
     * isLoadEndGone()为true，可以返回0
     * isLoadEndGone()为false，不能返回0
     */
    override fun getLoadEndViewId(): Int {
        return R.id.load_more_load_end_view
    }
}
