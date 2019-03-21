package com.kangce.finance.widget

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.PopupWindow
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.kangce.finance.R
import android.graphics.drawable.BitmapDrawable





class PopupWindowList {
    //传入List<String>类型的数据，以列表的形式展示
    private var mContext: Context? = null
    private var mPopWindow: PopupWindow? = null
    private var mRecycle: RecyclerView? = null
    private var mAdapter: Adapter? = null

    constructor(context: Context) {
        this.mContext = context
        val view = LayoutInflater.from(mContext).inflate(R.layout.pop_list, null)
        mRecycle = view.findViewById(R.id.recyclePop)
        mRecycle?.layoutManager = LinearLayoutManager(mContext)
        mAdapter = Adapter()
        mRecycle?.adapter = mAdapter
        mPopWindow = PopupWindow(view, WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT, true)
        mPopWindow?.isFocusable = true
        mPopWindow?.setBackgroundDrawable(BitmapDrawable())
        mPopWindow?.isOutsideTouchable = true
    }

    /**
     * 展示那些数据  list
     * 展示在哪里？ v
     */
    fun show(list: List<String>, v: View,msg:(String)->Unit) {
        mAdapter?.setNewData(list)
        mAdapter?.setOnItemClickListener { adapter, view, position ->
            msg.invoke(adapter.data[position] as String)
            mPopWindow?.dismiss()
        }
        mPopWindow?.showAsDropDown(v,0,-10)

    }

    class Adapter : BaseQuickAdapter<String, BaseViewHolder> {
        constructor() : super(R.layout.pop_item)

        override fun convert(helper: BaseViewHolder?, item: String?) {
            helper?.setText(R.id.tvItem, item)
        }
    }
}