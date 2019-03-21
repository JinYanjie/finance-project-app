package com.kangce.finance.ui.manager.userlevel

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.kangce.finance.bean.UserEntity
import com.kangce.finance.R

class UserLevelAdapter: BaseQuickAdapter<UserEntity, BaseViewHolder> {

    constructor():super(R.layout.item_user_list){

    }


    override fun convert(helper: BaseViewHolder?, item: UserEntity?) {
        helper?.setText(R.id.tv_userName,"用户名称: ${item?.username}")
        helper?.setText(R.id.tv_userPhone,"手机号码: ${item?.phone}")
        helper?.setText(R.id.tv_userLevel,"权限级别: ${item?.level}")

    }
}