package com.kangce.finance.choumou.http.service

import com.kangce.finance.bean.DataBean
import com.kangce.finance.bean.DepartmentBean
import com.kangce.finance.bean.LoginSuccess
import io.reactivex.Observable
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {

    /**
     * 获取all部门信息
     */
    @POST("/department/all")
    abstract fun getAllDepartment(): Observable<DataBean<List<DepartmentBean>>>

    /**
     * 添加部门信息
     */
    @POST("/department/add")
    abstract fun addDepartment(@Query("name") name: String): Observable<DataBean<Any>>

    /**
     * 添加部门信息
     */
    @POST("/department/delete")
    abstract fun delDepartment(@Query("id") id: Int): Observable<DataBean<Any>>

    /**
     * 修改部门信息
     */
    @POST("/department/update")
    abstract fun updateDepartment(@Query("id") id: Int, @Query("name") name: String): Observable<DataBean<Any>>


    /**
     * 用户注册
     */
    @POST("/auth/register")
    fun userRegister(@Query("phone") phone: String,
                     @Query("password") password: String
    ): Observable<DataBean<LoginSuccess>>

    /**
     * 用户登录
     */
    @POST("/auth/login")
    fun userLogin(@Query("phone") phone: String,
                     @Query("password") password: String
    ): Observable<DataBean<LoginSuccess>>

}