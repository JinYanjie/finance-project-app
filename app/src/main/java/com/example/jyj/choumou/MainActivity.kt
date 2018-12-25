package com.example.jyj.choumou

import android.os.Bundle
import android.webkit.WebView
import com.example.jyj.choumou.bean.LoginBean
import com.example.jyj.choumou.http.exceptition.ApiException
import com.example.jyj.choumou.http.observer.HttpRxObservable
import com.example.jyj.choumou.http.observer.HttpRxObserver
import com.example.jyj.choumou.http.ohkttp.RetrofitManager
import com.example.jyj.choumou.http.service.ApiService
import com.example.jyj.choumou.utils.L
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity :  RxAppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var webView = WebView(this)
        // Example of a call to a native method
        sample_text.text = stringFromJNI()
        var userInfo = RetrofitManager.retrofitManager.getRetrofit(this)
                .create(ApiService::class.java).getUserInfo("18868876997","123456",0,1)
        HttpRxObservable.getObservable(userInfo,this).subscribe(object : HttpRxObserver<LoginBean>() {
            override fun onStart(d: Disposable) {
                L.e("start")
            }

            override fun onError(e: ApiException) {
                L.e(e.toString())
            }

            override fun onSuccess(response: LoginBean) {
                L.e(response.toString())
            }

        })
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    external fun stringFromJNI(): String

    companion object {

        // Used to load the 'native-lib' library on application startup.
        init {
            System.loadLibrary("native-lib")
        }
    }
}
