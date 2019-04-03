package com.kangce.finance.ui.fixedassets

import android.content.Context
import android.content.Intent
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.kangce.finance.R
import com.kangce.finance.base.BaseActivity
import com.kangce.finance.base.BaseList.BaseListActivity
import com.kangce.finance.bean.FixedAssetsEntity
import com.kangce.finance.http.exceptition.ApiException
import com.kangce.finance.http.observer.HttpObserver
import com.kangce.finance.http.observer.HttpRxObservable
import com.kangce.finance.http.observer.HttpRxObserver
import com.kangce.finance.http.ohkttp.RetrofitManager
import com.kangce.finance.http.service.ApiService
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_common_list.*
import kotlinx.android.synthetic.main.title.*

class FixedAssetsActivity : BaseListActivity<FixedAssetsEntity>(), View.OnClickListener {


    override fun getLayoutId(): Int {
        return R.layout.activity_common_list
    }

    companion object {
        fun start(context:Context?){
            val intent = Intent(context, FixedAssetsActivity::class.java)
            context?.startActivity(intent)
        }
    }

    override fun initView() {
        super.initView()

        back.setOnClickListener(this)
        right_button.text = "添加"
        right_button.visibility = View.VISIBLE
        right_button.setOnClickListener(this)

        findViewById<TextView>(R.id.title).text ="固定资产列表"

    }

    override fun bindAdapter(): BaseQuickAdapter<FixedAssetsEntity, BaseViewHolder> {
        return FixedAssetsAdapter()
    }

    override fun getRecyclerView(): RecyclerView = recycler

    override fun getSwipeRefreshLayout(): SwipeRefreshLayout = refresh

    override fun onItemClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int) {

    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.back->{
                finish()
            }
            R.id.right_button->{
                AssetsEditActivity.start(this)
            }
        }
    }


    override fun loadListData() {
        HttpRxObservable
                .getObservable(RetrofitManager.retrofitManager.getRetrofit().create(ApiService::class.java).loadAllFixedAssetsList(),this)
                .subscribe(object :HttpRxObserver<List<FixedAssetsEntity>>(){
                    override fun onStart(d: Disposable) {
                    }

                    override fun onError(e: ApiException) {
                    }

                    override fun onSuccess(response: List<FixedAssetsEntity>) {
                        onSetLoadData(response)
                    }

                })
    }
}
