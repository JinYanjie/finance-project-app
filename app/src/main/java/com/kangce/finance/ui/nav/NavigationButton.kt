package com.kangce.finance.ui.nav

import android.content.Context
import android.os.Build
import android.support.annotation.DrawableRes
import android.support.annotation.RequiresApi
import android.support.annotation.StringRes
import android.support.v4.app.Fragment
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import com.kangce.finance.choumou.R

class NavigationButton : FrameLayout {
    var fragment: Fragment? = null
    var clx: Class<*>? = null
    private var mIconView: ImageView? = null
    private var mTitleView: TextView? = null
    private var mDot: TextView? = null
    private var mTag: String? = null
    private var mStrName: String? = null
    private var noNumberPoint: TextView? = null

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes) {
        init()
    }

    private fun init() {
        val inflater = LayoutInflater.from(context)
        inflater.inflate(R.layout.layout_nav_item, this, true)

        mIconView = findViewById(R.id.nav_iv_icon)
        mTitleView = findViewById(R.id.nav_tv_title)
        mDot = findViewById(R.id.nav_tv_dot)
        noNumberPoint = findViewById(R.id.nav_tv_noNumber_point)
    }

    override fun setSelected(selected: Boolean) {
        super.setSelected(selected)
        mIconView!!.isSelected = selected
        mTitleView!!.isSelected = selected
    }

    fun showRedDot(count: Int) {
        mDot!!.visibility = if (count > 0) View.VISIBLE else View.GONE
        mDot!!.text = count.toString()
    }

    fun showNoNumberPoint(show: Boolean?) {
        noNumberPoint!!.visibility = if (show!!) View.VISIBLE else View.GONE
    }

    fun init(@DrawableRes resId: Int, @StringRes strId: Int, clx: Class<*>) {
        mIconView!!.setImageResource(resId)
        mTitleView!!.setText(strId)
        this.clx = clx
        mStrName = resources.getString(strId)
        mTag = this.clx!!.name
    }

    override fun getTag(): String? {
        return mTag
    }

    fun getmStrName(): String? {
        return mStrName
    }

    fun setmStrName(mStrName: String) {
        this.mStrName = mStrName
    }
}
