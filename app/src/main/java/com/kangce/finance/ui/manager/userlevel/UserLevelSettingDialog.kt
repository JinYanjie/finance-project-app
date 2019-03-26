package com.kangce.finance.ui.manager.userlevel

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.kangce.finance.R
import com.kangce.finance.bean.UserEntity
import kotlinx.android.synthetic.main.dialog_user_level_change.*

class UserLevelSettingDialog : Dialog, View.OnClickListener {
    val userEntity: UserEntity
    val listener:(UserEntity)->Unit

    constructor(context: Context, userEntity: UserEntity, listener: (UserEntity) -> Unit) : super(context, R.style.MyDialogStyle) {
        this.userEntity = userEntity
        this.listener = listener
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.dialog_user_level_change)

//        val tv_cut = findViewById<TextView>(R.id.tv_cut)
//        val tv_add = findViewById<TextView>(R.id.tv_add)
//        val tv_levelShow = findViewById<TextView>(R.id.tv_levelShow)
//        val tv_cancel = findViewById<TextView>(R.id.tv_cancel)
//        val tv_confirm = findViewById<TextView>(R.id.tv_confirm)

        tv_cut.setOnClickListener(this)
        tv_add.setOnClickListener(this)
        tv_cancel.setOnClickListener(this)
        tv_confirm.setOnClickListener(this)

    }


    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.tv_cut -> {
                val level = userEntity.level
                if(level!=1){
                    userEntity.level = level-1
                }
                tv_levelShow.setText( userEntity.level.toString())
            }
            R.id.tv_add -> {
                val level = userEntity.level
                if(level!=3){
                    userEntity.level = level+1
                }
                tv_levelShow.setText( userEntity.level.toString())
            }
            R.id.tv_cancel -> {
                dismiss()
            }
            R.id.tv_confirm -> {
                dismiss()
                listener.invoke(userEntity)
            }
        }
    }


}