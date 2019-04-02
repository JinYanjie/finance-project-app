package com.kangce.finance.http.service

import com.kangce.finance.bean.*
import com.kangce.finance.bean.DataBean
import com.kangce.finance.bean.DepartmentBean
import com.kangce.finance.bean.LoginSuccess
import com.kangce.finance.bean.Staff
import io.reactivex.Observable
import okhttp3.RequestBody
import retrofit2.http.*
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

    /**
     * 用户列表
     */
    @POST("/auth/allUser")
    fun loadAllUser(): Observable<DataBean<List<UserEntity>>>

    /**
     * 修改用户权限
     */
    @POST("/auth/userLevelChange")
    fun changeUserLevel(@Query("userId") userId: Int,
                        @Query("newLevel") newLevel: Int
                        ): Observable<DataBean<Any>>


    /**
     * 获取所有固定资产列表
     */
    @POST("/fixedAssets/loadFixedAssetsList")
    fun loadAllFixedAssetsList():Observable<DataBean<List<FixedAssetsEntity>>>

    /**
     * 获取所有资产类型
     */
    @POST("/fixedAssets/loadAllAssetType")
    fun loadAllAssetType():Observable<DataBean<List<AssettypeEntity>>>
}