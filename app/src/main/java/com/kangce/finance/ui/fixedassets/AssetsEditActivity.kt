package com.kangce.finance.ui.fixedassets

import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.TextView
import com.kangce.finance.R
import com.kangce.finance.base.BaseActivity
import com.kangce.finance.bean.AssettypeEntity
import com.kangce.finance.http.exceptition.ApiException
import com.kangce.finance.http.observer.HttpObserver
import com.kangce.finance.http.observer.HttpRxObservable
import com.kangce.finance.http.ohkttp.RetrofitManager
import com.kangce.finance.http.service.ApiService
import com.kangce.finance.ui.fixedassets.dialog.ChoiceAssetsTypeDialog
import com.kangce.finance.ui.fixedassets.dialog.ChoiceDateDialog
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_assets_edit.*
import kotlinx.android.synthetic.main.layout_topbar_back_title.*

class AssetsEditActivity:BaseActivity(), View.OnClickListener {
    var assetstypeList:List<AssettypeEntity>? = null

    val choiceAssetsTypeDialog by lazy { ChoiceAssetsTypeDialog(this) }
    val choiceDateDialog by lazy { ChoiceDateDialog(this) }


    override fun getLayoutId(): Int = R.layout.activity_assets_edit


    companion object {
        fun start(context: Context){
            val intent = Intent(context, AssetsEditActivity::class.java)
            context.startActivity(intent)
        }
    }

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
                if(assetstypeList==null){
                    loadAssetsType()
                }else{
                    showAssetsTypeChoiceDialog()
                }

            }
            R.id.ll_pickInputTime->{
                choiceDateDialog.show()
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

    fun loadAssetsType(){
        HttpRxObservable
                .getObservable(RetrofitManager.retrofitManager.getRetrofit().create(ApiService::class.java).loadAllAssetType(),this)
                .subscribe(object:HttpObserver<List<AssettypeEntity>>(){
                    override fun onStart(d: Disposable) {
                    }
                    override fun onError(e: ApiException) {
                    }
                    override fun onSuccess(response: List<AssettypeEntity>) {
                        assetstypeList= response

                        showAssetsTypeChoiceDialog()
                    }
                })
    }

    fun showAssetsTypeChoiceDialog(){
        choiceAssetsTypeDialog.setData(assetstypeList)
        choiceAssetsTypeDialog.setOnConfrimListener {
            et_assetsType.setText(assetstypeList?.get(it)?.aname)
            et_assetsType.setTag(assetstypeList?.get(it))
        }
        choiceAssetsTypeDialog.show()
    }


}
