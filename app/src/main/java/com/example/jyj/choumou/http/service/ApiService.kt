package com.example.jyj.choumou.http.service

import com.example.jyj.choumou.bean.DataBean
import com.example.jyj.choumou.bean.LoginBean
import io.reactivex.Observable
import retrofit2.http.*

interface ApiService {
    @FormUrlEncoded
    @POST("/users/login")
    abstract fun getUserInfo(@Field("username") name: String,
                             @Field("password") password: String,
                             @Field("loginType") loginType: Int,
                             @Field("exp") exp: Int): Observable<DataBean<LoginBean>>

}