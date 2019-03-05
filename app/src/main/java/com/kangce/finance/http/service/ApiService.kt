package com.kangce.finance.choumou.http.service

import com.kangce.finance.bean.DataBean
import com.kangce.finance.bean.DepartmentBean
import io.reactivex.Observable
import retrofit2.http.*

interface ApiService {

    /**
     * 获取all部门信息
     */
    @POST("/department/all")
    abstract fun getAllDepartment():Observable<DataBean<List<DepartmentBean>>>

}