package com.kangce.finance.choumou.http.service

import com.kangce.finance.bean.DataBean
import com.kangce.finance.bean.DepartmentBean
import com.kangce.finance.bean.Staff
import io.reactivex.Observable
import retrofit2.http.*

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
    abstract fun updateDepartment(@Query("id") id: Int,@Query("name") name:String): Observable<DataBean<Any>>


    /**
     * 修改部门信息
     */
    @POST("/staff/all")
    abstract fun getAllStaff(@Query("pageNum") pageNum: Int,@Query("pageSize") pageSize:Int): Observable<DataBean<Staff>>


}