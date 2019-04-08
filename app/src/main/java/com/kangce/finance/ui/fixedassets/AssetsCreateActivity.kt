package com.kangce.finance.ui.fixedassets

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.text.TextUtils
import android.view.View
import android.widget.TextView
import com.bigkoo.pickerview.builder.OptionsPickerBuilder
import com.bigkoo.pickerview.builder.TimePickerBuilder
import com.bigkoo.pickerview.listener.OnOptionsSelectListener
import com.bigkoo.pickerview.listener.OnTimeSelectListener
import com.bigkoo.pickerview.view.TimePickerView
import com.hjq.toast.ToastUtils
import com.kangce.finance.R
import com.kangce.finance.base.BaseActivity
import com.kangce.finance.bean.AssetsChangeMode
import com.kangce.finance.bean.AssettypeEntity
import com.kangce.finance.bean.DepartmentBean
import com.kangce.finance.bean.FixedAssetsEntity
import com.kangce.finance.http.exceptition.ApiException
import com.kangce.finance.http.observer.HttpObserver
import com.kangce.finance.http.observer.HttpRxObservable
import com.kangce.finance.http.observer.HttpRxObserver
import com.kangce.finance.http.ohkttp.RetrofitManager
import com.kangce.finance.http.service.ApiService
import com.kangce.finance.ui.fixedassets.dialog.ChoiceAssetsTypeDialog
import com.kangce.finance.utils.T
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_assets_create.*
import kotlinx.android.synthetic.main.layout_topbar_back_title.*
import java.math.BigDecimal
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class AssetsCreateActivity : BaseActivity(), View.OnClickListener {
    var assetstypeList: List<AssettypeEntity>? = null
    var AssetsChangeModeList: List<AssetsChangeMode>? = null
    var departmentList: List<DepartmentBean>? = null


    val format by lazy { SimpleDateFormat("yyyy-MM-dd") }
    val choiceAssetsTypeDialog by lazy { ChoiceAssetsTypeDialog(this) }


    private var pvTime: TimePickerView? = null


    override fun getLayoutId(): Int = R.layout.activity_assets_create


    companion object {
        fun start(context: Context) {
            val intent = Intent(context, AssetsCreateActivity::class.java)
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
        tv_confirm.setOnClickListener(this)
    }

    override fun onClick(v: View?) {

        when (v?.id) {
            R.id.back -> {
                finish()
            }
            R.id.ll_pickAssetsType -> {
                if (assetstypeList == null) loadAssetsType() else showAssetsTypeChoiceDialog()
            }
            R.id.ll_pickInputTime -> {
                initTimePicker()
                pvTime?.show()
            }
            R.id.ll_pickUseStatus -> {
                showUseStatusPick()
            }
            R.id.ll_pickChange -> {
                if (AssetsChangeModeList == null) loadAllAssetChangeMode() else showAssetsChangePick()
            }
            R.id.ll_pickDepartment -> {
                if (departmentList == null) loadDepartmentList() else showDepartmentPick()
            }
            R.id.ll_pickDepreciationWay -> {
                showDepreciationModePick()
            }
            R.id.tv_confirm -> {
                var assetsName = et_assetsName.text.toString().trim()
                var assetstype = tv_assetsType.getTag()
                var assetsUnit = et_unit.text.toString().trim()
                var specification = et_specification.text.toString().trim()//规格
                var inputDate = tv_inputDate.getTag()//入账时间
                var assetsUseStatus = tv_assetsUseStatus.getTag()//使用状态
                var storagePosition = et_storagePosition.text.toString().trim()//存储地点
                var changeWay = tv_changeWay.getTag()//变更方式
                var purposeWay = et_purposeWay.text.toString().trim()//经济用途
                var supplier = et_supplier.text.toString().trim()//供应商
                var departmentTarget = tv_department.getTag()//使用部门
                var assetsRealPrice = et_AssetsRealPrice.text.toString().trim()//商品原值
                var depreciationWay = tv_depreciationWay.getTag()//折旧方式
                var user_time_count = et_user_time_count.text.toString().trim()//预计使用时间
                var monthDepreciation = et_monthDepreciation.text.toString().trim()//月折旧率

                if (TextUtils.isEmpty(assetsName)
                        || assetstype == null
                        || TextUtils.isEmpty(assetsUnit)
                        || TextUtils.isEmpty(specification)
                        || inputDate == null
                        || assetsUseStatus == null
                        || TextUtils.isEmpty(storagePosition)
                        || changeWay == null
                        || TextUtils.isEmpty(purposeWay)
                        || TextUtils.isEmpty(supplier)
                        || departmentTarget == null
                        || TextUtils.isEmpty(assetsRealPrice)
                        || depreciationWay == null
                ) {
                    T.Companion.showShort("请输入完整的资产信息")
                    return
                }

                val assettypeEntity = assetstype as AssettypeEntity
                val inputDDD = inputDate as Date
                val useStatus = assetsUseStatus as Int
                val assetsChangeMode = changeWay as AssetsChangeMode
                val departmentBean = departmentTarget as DepartmentBean
                val depreciationW = depreciationWay as Int



                val bean = FixedAssetsEntity()
                bean.assetsName = assetsName
                bean.assetsType = assettypeEntity.id
                bean.countUnit = assetsUnit
                bean.specification = specification
                bean.inputTime = inputDDD
                bean.useStatus = useStatus
                bean.storageArea = storagePosition
                bean.changeWay = assetsChangeMode.id
                bean.purposeWay = purposeWay
                bean.supplier = supplier
                bean.departmentUse = departmentBean.id
                bean.initialAssetValue = BigDecimal(assetsRealPrice)
                bean.depreciationWay = depreciationW


                //写入折旧参数
                if(depreciationW==1){
                    if(TextUtils.isEmpty(user_time_count) || TextUtils.isEmpty(monthDepreciation)){
                        T.Companion.showShort("请输入完整的折旧参数")
                        return
                    }
                    bean.estimatedUseTime = user_time_count.toInt()
                    bean.monthDepreciation = monthDepreciation.toFloat()
                }

                startAddAssets(bean)






            }

        }

    }

    /**
     * 上传新资产
     */
    private fun startAddAssets(bean: FixedAssetsEntity) {
        HttpRxObservable
                .getObservable(RetrofitManager.retrofitManager.getRetrofit().create(ApiService::class.java).addFixedAssets(bean), this)
                .subscribe(object : HttpObserver<Any>() {
                    override fun onStart(d: Disposable) {
                        showLoading()
                    }

                    override fun onError(e: ApiException) {
                        closeLoading()
                        T.showShort("编辑失败: ${e.msg}")
                    }

                    override fun onSuccess(response: Any) {
                        closeLoading()
                        T.showShort("编辑成功!")
                        finish()
                    }
                })
    }

    private fun showUseStatusPick() {
        val list = ArrayList<String>()
        list.add("使用中")
        list.add("未使用")
        list.add("不需用")
        list.add("经营性出租")

        val userStatusPick = OptionsPickerBuilder(this, object : OnOptionsSelectListener {
            override fun onOptionsSelect(options1: Int, options2: Int, options3: Int, v: View?) {
                tv_assetsUseStatus.text = list.get(options1)
                tv_assetsUseStatus.tag = options1+1
            }
        })
                .build<String>()
        userStatusPick.setPicker(list)
        userStatusPick.show()
    }

    private fun showDepreciationModePick() {
        val list = ArrayList<String>()
        list.add("不折旧")
        list.add("平均折旧法")


        val depreciationModePick = OptionsPickerBuilder(this, object : OnOptionsSelectListener {
            override fun onOptionsSelect(options1: Int, options2: Int, options3: Int, v: View?) {
                tv_depreciationWay.text = list.get(options1)
                tv_depreciationWay.tag = options1

                if(options1 == 1) ll_countDepreciationi.visibility = View.VISIBLE else ll_countDepreciationi.visibility = View.GONE


            }
        })
                .build<String>()
        depreciationModePick.setPicker(list)
        depreciationModePick.show()
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
                showDepartmentPick()
            }

        })
    }

    private fun showDepartmentPick() {
        if (departmentList == null) {
            return
        }

        val list = ArrayList<String>()
        for (item in departmentList!!) {
            list.add(item.name)
        }

        val userStatusPick = OptionsPickerBuilder(this, object : OnOptionsSelectListener {
            override fun onOptionsSelect(options1: Int, options2: Int, options3: Int, v: View?) {
                tv_department.text = list.get(options1)
                tv_department.tag = departmentList?.get(options1)
            }
        })
                .build<String>()
        userStatusPick.setPicker(list)
        userStatusPick.show()
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
                        showAssetsChangePick()
                    }
                })
    }

    private fun showAssetsChangePick() {
        if (AssetsChangeModeList == null) {
            return
        }

        val list = ArrayList<String>()
        for (item in AssetsChangeModeList!!) {
            list.add(item.mname)
        }

        val userStatusPick = OptionsPickerBuilder(this, object : OnOptionsSelectListener {
            override fun onOptionsSelect(options1: Int, options2: Int, options3: Int, v: View?) {
                tv_changeWay.text = list.get(options1)
                tv_changeWay.tag = AssetsChangeModeList?.get(options1)
            }
        })
                .build<String>()
        userStatusPick.setPicker(list)
        userStatusPick.show()
    }

    fun loadAssetsType() {
        HttpRxObservable
                .getObservable(RetrofitManager.retrofitManager.getRetrofit().create(ApiService::class.java).loadAllAssetType(), this)
                .subscribe(object : HttpObserver<List<AssettypeEntity>>() {
                    override fun onStart(d: Disposable) {
                    }

                    override fun onError(e: ApiException) {
                    }

                    override fun onSuccess(response: List<AssettypeEntity>) {
                        assetstypeList = response

                        showAssetsTypeChoiceDialog()
                    }
                })
    }

    fun showAssetsTypeChoiceDialog() {
        choiceAssetsTypeDialog.setData(assetstypeList)
        choiceAssetsTypeDialog.setOnConfrimListener {
            tv_assetsType.setText(assetstypeList?.get(it)?.aname)
            tv_assetsType.setTag(assetstypeList?.get(it))
        }
        choiceAssetsTypeDialog.show()
    }

    /**
     * 日期选择器
     */
    private fun initTimePicker() {
        //控制时间范围(如果不设置范围，则使用默认时间1900-2100年，此段代码可注释)
        //因为系统Calendar的月份是从0-11的,所以如果是调用Calendar的set方法来设置时间,月份的范围也要是从0-11
        val selectedDate = Calendar.getInstance()

        val startDate = Calendar.getInstance()
        startDate.set(2016, 0, 1)

        val endDate = Calendar.getInstance()
        endDate.set(2100, 12, 31)
        //时间选择器
        pvTime = TimePickerBuilder(this, object : OnTimeSelectListener {
            override fun onTimeSelect(date: Date?, v: View?) {
                // 这里回调过来的v,就是show()方法里面所添加的 View 参数，如果show的时候没有添加参数，v则为null
                val dateText = format.format(date)
                tv_inputDate.text = dateText
                tv_inputDate.setTag(date)
            }

        })
                .setType(booleanArrayOf(true, true, true, false, false, false))
                .setLabel("", "", "", "", "", "") //设置空字符串以隐藏单位提示   hide label
                .setDividerColor(Color.DKGRAY)
                .setContentTextSize(20)
                .setDate(selectedDate)
                .setRangDate(startDate, endDate)
                .setOutSideColor(0x00000000)
                .setOutSideCancelable(false)
                .build()

        pvTime?.setKeyBackCancelable(false)//系统返回键监听屏蔽掉
    }


}
