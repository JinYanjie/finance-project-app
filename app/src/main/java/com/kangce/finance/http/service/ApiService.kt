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
import java.util.*

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
     * 获取员工信息
     */
    @POST("/staff/all")
    abstract fun getAllStaff(@Query("pageNum") pageNum: Int, @Query("pageSize") pageSize: Int): Observable<DataBean<Staff>>

    /**
     * 添加信息
     */
    @POST("/staff/add")
    abstract fun addStaff(@Body info: RequestBody): Observable<DataBean<StaffBean>>

    /**
     * 获取已录入工资的员工
     */
    @POST("/salary/get/staffSalary")
    abstract fun getEnterStaff(@Query("pageNum") pageNum: Int, @Query("pageSize") pageSize: Int): Observable<DataBean<List<SalaryUserBean>>>

    /**
     * 获取未录入工资的员工
     */
    @POST("/salary/get/staffUnsalary")
    abstract fun getUnSalaryStaff(@Query("pageNum") pageNum: Int, @Query("pageSize") pageSize: Int): Observable<DataBean<List<SalaryUserBean>>>


    /**
     * 获取社保税率
     */
    @POST("/rate/get")
    fun getRate(): Observable<DataBean<List<SocialRateBean>>>

    /**
     * 添加信息
     */
    @POST("/salary/add")
    abstract fun addSalary(@Body info: RequestBody): Observable<DataBean<SalaryBean>>

    /**
     * 获取指定员工 工资详情
     */
    @POST("/salary/get")
    fun getStaffSalary(@Query("sId") id: Int): Observable<DataBean<SalaryBean>>

    /**
     * 用户注册
     */
    @POST("/auth/register")
    fun userRegister(@Query("phone") phone: String,
                     @Query("password") password: String
    ): Observable<DataBean<LoginSuccess>>

    /**
     * 用户登
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
     * 用户列表
     */
    @POST("/auth/getUser")
    fun getUserById(@Query("id") id: Int): Observable<DataBean<UserEntity>>

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
    fun loadAllFixedAssetsList(): Observable<DataBean<List<FixedAssetsEntity>>>

    /**
     * 根据id获取固定资产详情
     */
    @POST("/fixedAssets/loadFixedAssetsById")
    fun loadFixedAssetsById(@Query("assetsId") assetsId: Int): Observable<DataBean<FixedAssetsEntity>>

    /**
     * 获取所有资产类型
     */
    @POST("/fixedAssets/loadAllAssetType")
    fun loadAllAssetType(): Observable<DataBean<List<AssettypeEntity>>>

    /**
     * 获取所有资产变更方式
     */
    @POST("/fixedAssets/loadAllAssetChangeMode")
    fun loadAllAssetChangeMode(): Observable<DataBean<List<AssetsChangeMode>>>

    /**
     * 新增资产条目
     */
    @POST("/fixedAssets/add")
    fun addFixedAssets(@Body assets: FixedAssetsEntity): Observable<DataBean<Any>>

    /**
     * 修改资产变动方式
     */
    @POST("/fixedAssets/editChangeWay")
    fun editChangeWay(@Query("id") id: Int,
                      @Query("changWay") changWay: Int): Observable<DataBean<Any>>


    /**
     * 获取凭证字
     */
    @POST("/certificate/getWord")
    fun getWord(): Observable<DataBean<List<CertificateBean>>>

    /**
     * 获取凭证字
     */
    @POST("baseCourse/get")
    fun getBaseCourse(@Query("id") id: Int): Observable<DataBean<List<BaseCourseBean>>>

    /**
     * 新增凭证
     */
    @POST("/certificateManager/add")
    fun addCertificate(@Body certificateBeans: List<CertificateManagerBean>): Observable<DataBean<Any>>

    /**
     * 获取凭证
     */

    @POST("/certificateManager/get")
    fun getCertificate(@Query("state") state: Int): Observable<DataBean<List<CertificateListBean>>>

    /**
     * 审核凭证
     */

    @POST("/certificateManager/check")
    fun checkCertificate(@Query("certificateNum") certificateNum: String, @Query("currentNum") currentNum: Int,
                         @Query("userId") userId: Int, @Query("state") state: Int): Observable<DataBean<Any>>


    /**
     * 获取资产负债表
     */
    @POST("/sheet/getBalanceSheet")
    fun getBalanceSheet(@Query("type") type: Int): Observable<DataBean<List<BalanceSheetBean>>>

    /**
     * 获取损益表
     */
    @POST("/sheet/getIncomeSheet")
    fun getIncomeSheet(): Observable<DataBean<List<IncomeSheetBean>>>

    /**
     * 获取现金流量表
     */
    @POST("/sheet/getCashFlowSheet")
    fun getCashFlowSheet(): Observable<DataBean<List<IncomeSheetBean>>>

    /**
     * 获取资金分配表
     */
    @POST("/sheet/getAllocateSheet")
    fun getAllocateSheet(): Observable<DataBean<List<AllocateSalaryBean>>>
}