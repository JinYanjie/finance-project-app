package com.kangce.finance.choumou.http.service

import com.kangce.finance.bean.*
import io.reactivex.Observable
import okhttp3.RequestBody
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
     * 获取员工信息
     */
    @POST("/staff/all")
    abstract fun getAllStaff(@Query("pageNum") pageNum: Int,@Query("pageSize") pageSize:Int): Observable<DataBean<Staff>>

    /**
     * 添加信息
     */
    @POST("/staff/add")
    abstract fun addStaff( @Body info:RequestBody): Observable<DataBean<StaffBean>>

    /**
     * 获取已录入工资的员工
     */
    @POST("/salary/get/staffSalary")
    abstract fun getEnterStaff(): Observable<DataBean<List<SBean>>>


}