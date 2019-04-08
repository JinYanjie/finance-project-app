package com.kangce.finance.ui.fixedassets

import android.content.Context
import android.content.Intent
import android.view.View
import com.hjq.toast.ToastUtils
import com.kangce.finance.R
import com.kangce.finance.base.BaseActivity
import com.kangce.finance.bean.AssetsChangeMode
import com.kangce.finance.bean.DepartmentBean
import com.kangce.finance.bean.FixedAssetsEntity
import com.kangce.finance.http.exceptition.ApiException
import com.kangce.finance.http.observer.HttpObserver
import com.kangce.finance.http.observer.HttpRxObservable
import com.kangce.finance.http.observer.HttpRxObserver
import com.kangce.finance.http.ohkttp.RetrofitManager
import com.kangce.finance.http.service.ApiService
import com.kangce.finance.utils.T
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_assets_edit.*
import java.text.SimpleDateFormat
import java.util.*

class AssetsEditActivity : BaseActivity() {

    val format by lazy { SimpleDateFormat("yyyy-MM-dd") }

    override fun getLayoutId(): Int = R.layout.activity_assets_edit


    companion object {
        fun start(context: Context, id: Int) {
            val intent = Intent(context, AssetsEditActivity::class.java)
            intent.putExtra("assetsId", id)
            context.startActivity(intent)
        }
    }



    private var AssetsChangeModeList: List<AssetsChangeMode>?= null
    var departmentList: List<DepartmentBean>? = null


    private var assetsId: Int = -1
    var assetsEntity: FixedAssetsEntity? = null


    override fun initView() {
        super.initView()

        assetsId = intent.getIntExtra("assetsId", -1)

        if (assetsId == -1) {
            T.Companion.showShort("该资产id不存在")
            return
        }

        loadAllAssetChangeMode()

    }


    /**
     * 获取资产变动方式
     */
    private fun loadAllAssetChangeMode() {
        HttpRxObservable
                .getObservable(RetrofitManager.retrofitManager.getRetrofit().create(ApiService::class.java).loadAllAssetChangeMode(), this)
                .subscribe(object : HttpObserver<List<AssetsChangeMode>>() {
                    override fun onStart(d: Disposable) {
                    }

                    override fun onError(e: ApiException) {
                    }

                    override fun onSuccess(response: List<AssetsChangeMode>) {
                        AssetsChangeModeList = response
                        loadEntity()
                    }
                })
    }

    /**
     * 获取部门列表
     */
    private fun loadDepartmentList() {
        var departments = RetrofitManager.retrofitManager.getRetrofit()
                .create(ApiService::class.java).getAllDepartment()
        HttpRxObservable.getObservable(departments, this).subscribe(object : HttpRxObserver<List<DepartmentBean>>() {
            override fun onStart(d: Disposable) {
            }

            override fun onError(e: ApiException) {
                ToastUtils.show(e.msg)
            }

            override fun onSuccess(response: List<DepartmentBean>) {
                departmentList = response

                tv_department.text = response.get(assetsEntity?.departmentUse!!).name
            }

        })
    }

    private fun loadEntity() {
        HttpRxObservable
                .getObservable(RetrofitManager.retrofitManager.getRetrofit().create(ApiService::class.java).loadFixedAssetsById(assetsId), this)
                .subscribe(object : HttpObserver<FixedAssetsEntity>() {
                    override fun onStart(d: Disposable) {
                        showLoading()
                    }

                    override fun onError(e: ApiException) {
                        closeLoading()
                        T.showShort(e.msg)
                    }

                    override fun onSuccess(response: FixedAssetsEntity) {
                        closeLoading()
                        assetsEntity = response
                        initViewData()
                        loadDepartmentList()
                    }
                })


    }

    private fun initViewData() {
        if (assetsEntity==null){
            return
        }

        et_assetsName.text = assetsEntity?.assetsName
        tv_assetsType.text = when(assetsEntity?.assetsType) {
            1->"房屋及建筑物"
            2->"生产设备"
            3->"办公设备"
            4->"运输设备"
            5->"电子设备"
            else->"未知类型"
        }
        et_unit.text = assetsEntity?.countUnit
        et_specification.text = assetsEntity?.specification
        tv_inputDate.text = format.format(assetsEntity?.inputTime)
        tv_assetsUseStatus.text = assetsEntity?.useStatus.toString()
        et_storagePosition.text = assetsEntity?.storageArea

        val changeMode = AssetsChangeModeList?.get(assetsEntity?.changeWay ?: -1)
        tv_changeWay.text =changeMode?.mname

        et_purposeWay.text = assetsEntity?.purposeWay
        et_supplier.text = assetsEntity?.supplier
        et_AssetsRealPrice.text = assetsEntity?.initialAssetValue?.toDouble().toString()
        tv_depreciationWay.text = when(assetsEntity?.depreciationWay){
            0->"不折旧"
            1->"平均折旧法"
            else->"方法错误"
        }


        if(assetsEntity?.depreciationWay!=0){
            ll_countDepreciationi.visibility = View.VISIBLE

            et_user_time_count.text = assetsEntity?.estimatedUseTime.toString()
            et_monthDepreciation.text = assetsEntity?.monthDepreciation.toString()
            val monthDifferent = monthDifferent(assetsEntity?.inputTime!!, Date())
            val initValue = assetsEntity?.initialAssetValue?.toDouble() ?:0.0
            val nowValue = initValue - (initValue * (monthDifferent * assetsEntity?.monthDepreciation!!))
            et_worthCount.text = nowValue.toString()

        }else{
            ll_countDepreciationi.visibility = View.GONE
            et_worthCount.text = assetsEntity?.initialAssetValue.toString()
        }
    }

    /**
     * 计算两个时间之间的月差
     */
    private fun monthDifferent(startDate:Date,endDate:Date): Int {
        val yearFormat = SimpleDateFormat("yyyy")
        val monthFormat = SimpleDateFormat("MM")

        val differentYear = yearFormat.format(endDate).toInt() - yearFormat.format(startDate).toInt()
        val differentMonth = monthFormat.format(endDate).toInt() - monthFormat.format(startDate).toInt()

        return differentYear*12+differentMonth
    }

}
