package com.example.textphtp

import com.example.phttp.http.bean.DataBean
import io.reactivex.Observable
import retrofit2.http.POST
import retrofit2.http.Query


interface ApiService {


    /**
     * 用户登录
     */
    @POST("/auth/login")
    fun userLogin(@Query("phone") phone: String,
                  @Query("password") password: String
    ): Observable<DataBean<LoginSuccess>>

}