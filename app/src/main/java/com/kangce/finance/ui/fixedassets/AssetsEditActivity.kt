package com.kangce.finance.ui.fixedassets

import android.view.View
import android.widget.TextView
import com.kangce.finance.R
import com.kangce.finance.base.BaseActivity
import kotlinx.android.synthetic.main.activity_assets_edit.*
import kotlinx.android.synthetic.main.layout_topbar_back_title.*

class AssetsEditActivity:BaseActivity(), View.OnClickListener {


    override fun getLayoutId(): Int = R.layout.activity_assets_edit


    override fun initView() {
        super.initView()

        back.setOnClickListener(this)
        findViewById<TextView>(R.id.title).text = "编辑固定资产条目"
        ll_pickAssetsType.setOnClickListener(this)
        ll_pickInputTime.setOnClickListener(this)
        ll_pickUseStatus.setOnClickListener(this)
        ll_pickChange.setOnClickListener(this)
        ll_pickDepartment.setOnClickListener(this)
        ll_pickDepreciationWay.setOnClickListener(this)
    }

    override fun onClick(v: View?) {

        when(v?.id){
            R.id.back->{
               finish()
            }
            R.id.ll_pickAssetsType->{

            }
            R.id.ll_pickInputTime->{

            }
            R.id.ll_pickUseStatus->{

            }
            R.id.ll_pickChange->{

            }
            R.id.ll_pickDepartment->{

            }
            R.id.ll_pickDepreciationWay->{

            }

        }

    }


}
