package com.kangce.finance.ui.manager.userlevel

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.kangce.finance.bean.UserEntity
import com.kangce.finance.choumou.R

class UserLevelAdapter: BaseQuickAdapter<UserEntity, BaseViewHolder> {

    constructor():super(R.layout.item_user_list){

    }


    override fun convert(helper: BaseViewHolder?, item: UserEntity?) {

    }
}