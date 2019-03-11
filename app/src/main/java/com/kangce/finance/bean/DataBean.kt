package com.kangce.finance.bean
data class DataBean<T>(
    val code: Int=0,
    val `data`: T,
    val msg: String="",
    val time: String=""
)


data class LoginSuccess(var token:String,var user:UserEntity): BaseEntity()
