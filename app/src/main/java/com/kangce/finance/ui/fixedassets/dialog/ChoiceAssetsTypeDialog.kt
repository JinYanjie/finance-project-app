package com.kangce.finance.ui.fixedassets.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.Display
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import cn.qqtheme.framework.widget.WheelView
import com.kangce.finance.R
import com.kangce.finance.bean.AssettypeEntity
import com.kangce.finance.utils.AppUtils
import com.kangce.finance.utils.DisplayUtil
import kotlinx.android.synthetic.main.dialog_choice_assatetype.*

class ChoiceAssetsTypeDialog : Dialog, View.OnClickListener {


    private var listener: ((Int) -> Unit)? = null
    private var currentIndex = 0


    private var nameList: ArrayList<String>? = null



    constructor(context: Context) : super(context,R.style.bottomDialog) {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val attributes = window.attributes
        attributes.width = DisplayUtil.getScreenWidth(context)
        attributes.gravity = Gravity.BOTTOM
        window.attributes = attributes


        setContentView(R.layout.dialog_choice_assatetype)

        tv_cancel.setOnClickListener(this)
        tv_save.setOnClickListener(this)
        ll_root.setOnClickListener(this)

        wv_wheel.setItems(nameList)
        wv_wheel.setOffset(3)
        wv_wheel.setCycleDisable(true)
        wv_wheel.setTextSize(20f)
        wv_wheel.setUseWeight(true)
        val dividerConfig = WheelView.DividerConfig()
        dividerConfig.setRatio(WheelView.DividerConfig.FILL)
        wv_wheel.setDividerConfig(dividerConfig)

        wv_wheel.setOnItemSelectListener(WheelView.OnItemSelectListener { index ->
            currentIndex = index
        })
        wv_wheel.setSelectedIndex(0)


    }

    fun setData(list: List<AssettypeEntity>?) {
        if (list == null) {
            return
        }

         nameList = ArrayList<String>()
        for (item in list) {
            nameList?.add(item.aname)
        }




    }

    override fun show() {
        super.show()
    }


    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.tv_cancel,R.id.ll_root -> {
                dismiss()
            }
            R.id.tv_save -> {
                dismiss()
                listener?.invoke(currentIndex)
            }
        }
    }

    fun setOnConfrimListener(listener: (Int) -> Unit) {
        this.listener = listener
    }

}