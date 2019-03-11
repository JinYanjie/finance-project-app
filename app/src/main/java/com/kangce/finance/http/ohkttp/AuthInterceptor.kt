package com.kangce.finance.choumou.http.ohkttp

import android.text.TextUtils
import com.kangce.finance.Constant
import com.kangce.finance.MyApp
import com.kangce.finance.choumou.utils.AppUtils
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor:Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        try {
                var originalRequest = chain.request()
                val authorised = originalRequest.newBuilder().header("platform", "2")

                var token=""
                if (TextUtils.isEmpty(token)){
                    authorised.header("Authorization", Constant.TOKEN)
                }

                try {
                    val pm = MyApp.instance().packageManager//context为当前Activity上下文
                    var packageInfo = pm?.getPackageInfo(MyApp.instance().packageName, 0)
                    val version=packageInfo?.versionName
                    authorised.header("version", version)
                } catch (e: Exception) {
                }

                try {
                    val deviceId= AppUtils.getDeviceId(MyApp.instance())
                    authorised.header("deviceId", deviceId)
                } catch (e: Exception) {
                }

                val request = authorised.build()
                return chain.proceed(request)
        } catch (e: Exception) {
        }
        return chain.proceed(chain.request())
    }
}