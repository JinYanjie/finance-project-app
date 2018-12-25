package com.example.jyj.choumou.bean
data class DataBean<T>(
    val code: Int,
    val `data`: T,
    val msg: String,
    val time: String
)
