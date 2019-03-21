package com.kangce.finance.widget

import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.kangce.finance.base.BaseList.BaseListActivity
import com.kangce.finance.R


class MEmptyView<T> {
    private var mListener: BaseListActivity<T>? = null
    private val mLayoutInflater: LayoutInflater
    var view: View? = null
    private var mode = "通用"

    constructor(listListener: BaseListActivity<T>?) {
        this.mListener = listListener
        mLayoutInflater = LayoutInflater.from(listListener)
        init()
    }

    constructor(listListener: BaseListActivity<T>?, mode: String) {
        this.mListener = listListener
        mLayoutInflater = LayoutInflater.from(listListener)
        this.mode = mode
        init()
    }

    private fun init() {

        var title = "该页面暂无内容"
        var imageId = R.drawable.ic_empty_tongyong

        when (mode) {
            "通用" -> {
                title = "该页面暂无内容"
                imageId = R.drawable.ic_empty_tongyong
            }


        }


        view = mLayoutInflater.inflate(R.layout.layout_list_empty_view, null, false)
        val img_show = view!!.findViewById<ImageView>(R.id.img_show)
        val tv_title = view!!.findViewById<TextView>(R.id.tv_title)

        img_show.setImageResource(imageId)
        tv_title.text = title

        //            // 点击主动刷新
        if (view != null) {
            view!!.setOnClickListener { mListener!!.startRefresh() }
        }

    }
}
