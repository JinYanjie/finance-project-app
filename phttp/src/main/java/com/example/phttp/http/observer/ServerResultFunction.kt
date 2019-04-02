package com.example.phttp.http.observer

import com.example.phttp.http.bean.DataBean
import com.example.phttp.http.exceptition.ServerException
import io.reactivex.annotations.NonNull
import io.reactivex.functions.Function

class ServerResultFunction<T> : Function<DataBean<T>, T> {
    @Throws(Exception::class)
    override fun apply(@NonNull response: DataBean<T>): T {
        //打印服务器回传结果
        //        L.e(response.toString());
        if (response.code != 200) {
            throw ServerException(response.code, response.msg)
        }
        return response.data
    }

}