package com.kangce.finance.http.observer

import com.kangce.finance.bean.DataBean
import com.kangce.finance.http.exceptition.ServerException
import com.kangce.finance.utils.L
import io.reactivex.annotations.NonNull
import io.reactivex.functions.Function

class ServerResultFunction<T> : Function<DataBean<T>, T> {
    @Throws(Exception::class)
    override fun apply(@NonNull response: DataBean<T>): T {
        //打印服务器回传结果
        //        L.e(response.toString());
        if (response.code != 200) {
            L.e("AAA response="+response.toString())
            throw ServerException(response.code, response.msg)
        }
        return response.data
    }

}