package com.kangce.finance.widget

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.text.Html
import android.view.View
import android.widget.TextView
import com.kangce.finance.R

class DialogMessageHint private constructor(builder: Builder) : Dialog(builder.context, R.style.MyDialogStyle) {

    private var mContext: Context?=null
    private var listener: OnClickListener? = null
    private var cancel: TextView? = null
    private var confirm: TextView? = null
    private var tv_msg: TextView? = null
    private var isHtmlMode: Boolean
    private var htmlMsg: String?
    private var msg: String?
    private var leftText: String?
    private var rightText: String?


    init {
        this.mContext = builder.context
        this.msg = builder.msg
        this.leftText = builder.leftText
        this.rightText = builder.rightText
        this.htmlMsg = builder.htmlMsg
        this.isHtmlMode = builder.isHtmlMode
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val view = View.inflate(context, R.layout.dialog_message_hint, null)
        setContentView(view)

        cancel = view.findViewById(R.id.cancel)
        cancel!!.text = leftText
        confirm = view.findViewById(R.id.confirm)
        confirm!!.text = rightText

        tv_msg = view.findViewById(R.id.tv_msg)

        if (isHtmlMode) {
            tv_msg!!.text = Html.fromHtml(htmlMsg)
        } else {
            tv_msg!!.text = msg
        }


        cancel!!.setOnClickListener {
            if (listener != null) {
                listener!!.onLeftClick()
            }
            dismiss()
        }

        confirm!!.setOnClickListener {
            if (listener != null) {
                listener!!.onRightClick()
            }
            dismiss()
        }
    }

    fun setOnClickListener(listener: OnClickListener?) {
        this.listener = listener
    }

    interface OnClickListener {
        fun onLeftClick()

        fun onRightClick()
    }

    class Builder( val context: Context) {
         var msg: String?= null
         var htmlMsg: String? = null
         var isHtmlMode = false
         var leftText: String? = "取消"
         var rightText: String? = "确定"


        fun setMsg(msg: String?): Builder {
            this.msg = msg
            return this
        }

        fun setLeftText(leftText: String?): Builder {
            this.leftText = leftText
            return this
        }

        fun setRightText(rightText: String?): Builder {
            this.rightText = rightText
            return this
        }

        fun setHtmlMsg(htmlMsg: String?): Builder {
            this.htmlMsg = htmlMsg
            return this
        }

        fun setHtmlMode(htmlMode: Boolean): Builder {
            isHtmlMode = htmlMode
            return this
        }

        fun build(): DialogMessageHint {
            return DialogMessageHint(this)
        }
    }

}
