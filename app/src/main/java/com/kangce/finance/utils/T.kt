package com.kangce.finance.utils

import android.content.Context
import android.widget.Toast

/**
 * @description: T Toast统一管理类
 * @data 2018/1/24-15:31
 * @author: AoJiaoQiang
 */
class T private constructor() {

    companion object {

        var mContext: Context? = null
        private var toast: Toast? = null

        fun init(context: Context) {
            mContext = context
        }

        /**
         * 短时间显示Toast
         */
        fun showShort(message: CharSequence) {
            if (mContext == null) {
                throw RuntimeException("unRegister Context in Application")
            }
            if (toast != null) {
                toast!!.cancel()
            }
            toast = Toast.makeText(mContext, message, Toast.LENGTH_SHORT)
            toast!!.setText(message)
            toast!!.show()
        }

        fun showShort(resId: Int) {
            showShort(mContext!!.getString(resId))
        }

        fun showShort(msg: String) {
            if (mContext == null) {
                throw RuntimeException("unRegister Context in Application")
            }
            if (toast != null) {
                toast!!.cancel()
            }
            toast = Toast.makeText(mContext, msg, Toast.LENGTH_SHORT)
            toast!!.setText(msg)
            toast!!.show()
        }
    }
}