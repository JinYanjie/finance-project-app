package com.kangce.finance.choumou.http.ohkttp

import android.content.Context
import com.kangce.finance.Constant
import com.kangce.finance.MyApp
import com.kangce.finance.choumou.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.internal.platform.Platform
import okhttp3.internal.platform.Platform.WARN
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import java.io.IOException
import java.io.InputStream
import java.security.KeyStore
import java.security.SecureRandom
import java.security.cert.CertificateFactory
import java.util.concurrent.TimeUnit
import javax.net.ssl.SSLContext
import javax.net.ssl.SSLSocketFactory
import javax.net.ssl.TrustManagerFactory

class RetrofitManager private constructor(){

    companion object {
        private const val TIMEOUT: Long = 60
        val retrofitManager:RetrofitManager by lazy(mode=LazyThreadSafetyMode.SYNCHRONIZED) {
            RetrofitManager()
        }
    }

    // 设置okHttp
    private fun okHttpClient(check: Boolean): OkHttpClient {

        val builder = OkHttpClient.Builder()
                .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(TIMEOUT, TimeUnit.SECONDS)
        builder.addInterceptor(AuthInterceptor())
        // 如果是debug模式就开启日志


        val loginInterceptor = HttpLoggingInterceptor(HttpLoggingInterceptor.Logger { message -> Platform.get().log(WARN, message, null) })
        loginInterceptor.level = HttpLoggingInterceptor.Level.BODY
        if (BuildConfig.DEBUG) {
            //开启Log
            builder.addInterceptor(loginInterceptor)
        }

        if (check) {
            try {
                var factory: SSLSocketFactory? = null
                factory = getSSLSocketFactory(MyApp.instance().assets.open(Constant.CER_API))
                if (factory != null) {
                    builder.sslSocketFactory(factory)
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }

            builder.hostnameVerifier { hostname, session ->
                Constant.BASE_URL.contains(hostname)
            }
        }
        return builder.build()
    }

    fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
                .client(okHttpClient(false))
                .baseUrl(Constant.BASE_URL)
                .addConverterFactory(CustomGsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
    }


    //访问三方 可替换url
    fun getRetrofit(url: String): Retrofit {
        return Retrofit.Builder()
                .client(okHttpClient(false))
                .baseUrl(url)
                .addConverterFactory(CustomGsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
    }

    // 设置https证书
    private fun getSSLSocketFactory(vararg certificates: InputStream): SSLSocketFactory? {
        try {
            val certificateFactory = CertificateFactory.getInstance("X.509")
            val keyStore = KeyStore.getInstance(KeyStore.getDefaultType())
            keyStore.load(null)
            var index = 0
            for (certificate in certificates) {
                val certificateAlias = Integer.toString(index++)
                keyStore.setCertificateEntry(certificateAlias, certificateFactory.generateCertificate(certificate))

                try {
                    certificate?.close()
                } catch (e: IOException) {
                }

            }
            val sslContext = SSLContext.getInstance("TLS")
            val trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm())
            trustManagerFactory.init(keyStore)
            sslContext.init(null, trustManagerFactory.trustManagers, SecureRandom())
            return sslContext.socketFactory
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }
}