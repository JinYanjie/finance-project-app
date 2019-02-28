package com.kangce.finance.choumou.http.service

import com.kangce.finance.bean.DataBean
import com.kangce.finance.bean.DepartmentBean
import com.kangce.finance.bean.LoginBean
import io.reactivex.Observable
import retrofit2.http.*

interface ApiService {
    @FormUrlEncoded
    @POST("/users/login")
    abstract fun getUserInfo(@Field("username") name: String,
                             @Field("password") password: String,
                             @Field("loginType") loginType: Int,
                             @Field("exp") exp: Int): Observable<DataBean<LoginBean>>

    /**
     * 获取all部门信息
     */
    @POST("/department/all")
    abstract fun getAllDepartment():Observable<DataBean<List<DepartmentBean>>>

}