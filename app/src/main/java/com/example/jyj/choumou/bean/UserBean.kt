package com.example.jyj.choumou.bean

data class LoginBean(
    val isFirst: Int,
    val status: Int,
    val token: String,
    val type: Int,
    val uid: Int
)