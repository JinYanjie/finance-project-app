package com.kangce.finance.ui.main.fragment

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.kangce.finance.bean.ManagerItemBean
import com.kangce.finance.choumou.R

class ManagerItemAdapter:BaseQuickAdapter<ManagerItemBean,BaseViewHolder> {
    constructor():super(R.layout.item_manager)
    override fun convert(helper: BaseViewHolder?, item: ManagerItemBean?) {
        if (item!=null){
            with(item){
                helper?.setText(R.id.tvName,name)
                helper?.setImageResource(R.id.img,imgId)
            }
        }
    }

}