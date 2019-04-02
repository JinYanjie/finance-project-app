package com.kangce.finance.ui.fixedassets.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.View
import cn.qqtheme.framework.widget.WheelView
import com.kangce.finance.R
import kotlinx.android.synthetic.main.dialog_choice_assatetype.*

class ChoiceAssetsTypeDialog:Dialog, View.OnClickListener {


    constructor(context:Context):super(context){

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_choice_assatetype)

        tv_cancel.setOnClickListener(this)
        tv_save.setOnClickListener(this)


//        wv_wheel.setItems()
        wv_wheel.setOffset(3)
        wv_wheel.setCycleDisable(true)
        wv_wheel.setTextSize(20f)
        wv_wheel.setUseWeight(true)
        val dividerConfig = WheelView.DividerConfig()
        dividerConfig.setRatio(WheelView.DividerConfig.FILL)
        wv_wheel.setDividerConfig(dividerConfig)

        wv_wheel.setOnItemSelectListener(WheelView.OnItemSelectListener { index ->

        })
        wv_wheel.setSelectedIndex(0)

    }


    override fun onClick(v: View?) {
        when(v?.id){
            R.id.tv_cancel->{
                dismiss()
            }
            R.id.tv_save->{
                dismiss()
            }
        }
    }

}