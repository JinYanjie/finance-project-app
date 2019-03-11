package com.kangce.finance.choumou.http.ohkttp

import android.content.Context
import android.text.TextUtils
import com.kangce.finance.Constant
import com.kangce.finance.choumou.utils.AppUtils
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(context: Context):Interceptor {
    var mContext: Context?=null
    init {
        this.mContext=context
    }
    override fun intercept(chain: Interceptor.Chain): Response {
        try {
            if (mContext!=null){
                var originalRequest = chain.request()
                val authorised = originalRequest.newBuilder().header("platform", "2")

                var token=""
                if (TextUtils.isEmpty(token)){
                    authorised.header("Authorization", Constant.TOKEN)
                }

                try {
                    val pm = mContext?.packageManager//context为当前Activity上下文
                    var packageInfo = pm?.getPackageInfo(mContext?.packageName, 0)
                    val version=packageInfo?.versionName
                    authorised.header("version", version)
                } catch (e: Exception) {
                }

                try {
                    val deviceId= AppUtils.getDeviceId(mContext!!)
                    authorised.header("deviceId", deviceId)
                } catch (e: Exception) {
                }

                val request = authorised.build()
                return chain.proceed(request)
            }
        } catch (e: Exception) {
        }
        return chain.proceed(chain.request())
    }
}