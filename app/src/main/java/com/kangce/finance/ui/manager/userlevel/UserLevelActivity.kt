package com.kangce.finance.ui.manager.userlevel

import android.content.Context
import android.content.Intent
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v4.widget.TextViewCompat
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.kangce.finance.base.BaseActivity
import com.kangce.finance.base.BaseList.BaseListActivity
import com.kangce.finance.bean.UserEntity
import com.kangce.finance.R
import com.kangce.finance.bean.LoginSuccess
import com.kangce.finance.http.exceptition.ApiException
import com.kangce.finance.http.observer.HttpRxObservable
import com.kangce.finance.http.observer.HttpRxObserver
import com.kangce.finance.http.ohkttp.RetrofitManager
import com.kangce.finance.http.service.ApiService
import com.kangce.finance.utils.T
import io.reactivex.disposables.Disposable

import kotlinx.android.synthetic.main.activity_user_levle_setting.*
import kotlinx.android.synthetic.main.title.*

class UserLevelActivity:BaseListActivity<UserEntity>() {

    val userLevelAdapter by lazy { UserLevelAdapter() }

    override fun getLayoutId(): Int = R.layout.activity_user_levle_setting


    companion object {
        fun start(context: Context?) {
            val intent = Intent(context, UserLevelActivity::class.java)
            context?.startActivity(intent)
        }
    }

    override fun initView() {
        super.initView()

        findViewById<TextView>(R.id.title).text = "全部用户"
        back.setOnClickListener {
            finish()
        }

        userLevelAdapter.setOnItemLongClickListener { adapter, view, position ->
            val userEntity = userLevelAdapter.data.get(position)
            UserLevelSettingDialog(this,userEntity){
                setUserLevel(userEntity)
            }.show()

            return@setOnItemLongClickListener true
        }
    }

    private fun setUserLevel(userEntity: UserEntity) {
        var observer = object : HttpRxObserver<Any>(){
            override fun onStart(d: Disposable) {
                showLoading()
            }

            override fun onError(e: ApiException) {
                closeLoading()
                showToast(e.msg)
            }

            override fun onSuccess(response: Any) {
                closeLoading()
                T.Companion.showShort("修改成功!")
                authRefresh()
            }
        }

        val observable = RetrofitManager
                .retrofitManager
                .getRetrofit()
                .create(ApiService::class.java)
                .changeUserLevel(userEntity.id,userEntity.level)

        HttpRxObservable
                .getObservable(observable,this)
                .subscribe(observer)
    }


    override fun bindAdapter(): BaseQuickAdapter<UserEntity, BaseViewHolder> {
        return userLevelAdapter
    }

    override fun getRecyclerView(): RecyclerView? = recycler

    override fun getSwipeRefreshLayout(): SwipeRefreshLayout? = refresh

    override fun loadListData() {
        var observer = object : HttpRxObserver<List<UserEntity>>(){
            override fun onStart(d: Disposable) {
               showLoading()
            }

            override fun onError(e: ApiException) {
                closeLoading()
                showToast(e.msg)
            }

            override fun onSuccess(response: List<UserEntity>) {
                closeLoading()

                onSetLoadData(response)
            }
        }

        val observable = RetrofitManager
                .retrofitManager
                .getRetrofit()
                .create(ApiService::class.java)
                .loadAllUser()

        HttpRxObservable
                .getObservable(observable,this)
                .subscribe(observer)
    }



    override fun onItemClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
    }


}