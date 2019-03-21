package com.kangce.finance.ui.auth

import com.kangce.finance.base.IBasePresenter
import com.kangce.finance.base.IBaseView
import com.kangce.finance.bean.LoginSuccess
import com.kangce.finance.http.exceptition.ApiException
import com.kangce.finance.http.observer.HttpRxObservable
import com.kangce.finance.http.observer.HttpRxObserver
import com.kangce.finance.http.ohkttp.RetrofitManager
import com.kangce.finance.http.service.ApiService
import com.trello.rxlifecycle2.LifecycleProvider
import io.reactivex.disposables.Disposable

interface LoginContract {

    interface View:IBaseView{

        fun onRegisterSuccess(success:LoginSuccess)

        fun onLoginSuccess(success:LoginSuccess)
    }

    interface Presenter{

        fun register(phone:String,password:String)

        fun loginStart(phone: String, password: String)

    }

}


class LoginPresenter(view: LoginContract.View, lifeCycler: LifecycleProvider<*>?) : IBasePresenter<LoginContract.View, LifecycleProvider<*>>(view, lifeCycler),LoginContract.Presenter{


    override fun register(phone: String, password: String) {

        var observer = object :HttpRxObserver<LoginSuccess>(){
            override fun onStart(d: Disposable) {
                getView()?.showLoading()
            }

            override fun onError(e: ApiException) {
                getView()?.closeLoading()
                getView()?.showToast(e.msg)
            }

            override fun onSuccess(response: LoginSuccess) {
                getView()?.closeLoading()
                getView()?.onRegisterSuccess(response)
            }
        }

        val observable = RetrofitManager
                .retrofitManager
                .getRetrofit()
                .create(ApiService::class.java)
                .userRegister(phone, password)

        HttpRxObservable
                .getObservable(observable,getLifecycle())
                .subscribe(observer)

    }



    override fun loginStart(phone: String, password: String) {

        var observer = object :HttpRxObserver<LoginSuccess>(){
            override fun onStart(d: Disposable) {
                getView()?.showLoading()
            }

            override fun onError(e: ApiException) {
                getView()?.closeLoading()
                getView()?.showToast(e.msg)
            }

            override fun onSuccess(response: LoginSuccess) {
                getView()?.closeLoading()
                getView()?.onLoginSuccess(response)
            }
        }

        val observable = RetrofitManager
                .retrofitManager
                .getRetrofit()
                .create(ApiService::class.java)
                .userLogin(phone, password)

        HttpRxObservable
                .getObservable(observable,getLifecycle())
                .subscribe(observer)

    }


}