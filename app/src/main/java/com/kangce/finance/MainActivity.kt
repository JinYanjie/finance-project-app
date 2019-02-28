package com.kangce.finance

import android.os.Bundle
import com.example.jyj.choumou.R
import com.kangce.finance.bean.DepartmentBean
import com.kangce.finance.choumou.http.exceptition.ApiException
import com.kangce.finance.choumou.http.observer.HttpRxObservable
import com.kangce.finance.choumou.http.observer.HttpRxObserver
import com.kangce.finance.choumou.http.ohkttp.RetrofitManager
import com.kangce.finance.choumou.http.service.ApiService
import com.kangce.finance.utils.L
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity
import io.reactivex.disposables.Disposable

class MainActivity :  RxAppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        getDepartment()
    }

    private fun getDepartment() {
        var departments = RetrofitManager.retrofitManager.getRetrofit(this)
                .create(ApiService::class.java).getAllDepartment()
        HttpRxObservable.getObservable(departments, this).subscribe(object : HttpRxObserver<List<DepartmentBean>>() {
            override fun onStart(d: Disposable) {
                L.d("start")
            }

            override fun onError(e: ApiException) {

                L.e(e.toString())
            }

            override fun onSuccess(response: List<DepartmentBean>) {
                L.e(response.toString())
            }

        })
    }

}
