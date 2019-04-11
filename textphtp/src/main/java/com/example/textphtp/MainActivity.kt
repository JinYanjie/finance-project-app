package com.example.textphtp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.phttp.http.exceptition.ApiException
import com.example.phttp.http.observer.HttpRxObservable
import com.example.phttp.http.observer.HttpRxObserver
import com.example.phttp.http.ohkttp.RetrofitManager
import io.reactivex.disposables.Disposable

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loginStart("18877889966", "123123")
    }

    fun loginStart(phone: String, password: String) {

        var observer = object : HttpRxObserver<LoginSuccess>() {
            override fun onStart(d: Disposable) {
            }
            override fun onError(e: ApiException) {

            }

            override fun onSuccess(response: LoginSuccess) {

            }
        }

        val observable = RetrofitManager
                .getInstance(this)
                .getRetrofit(AuthInterceptor())
                .create(ApiService::class.java)
                .userLogin(phone, password)

        HttpRxObservable
                .getObservable(observable, null)
                .subscribe(observer)

    }
}
