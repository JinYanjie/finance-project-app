package com.kangce.finance.bean

/**
 *
 */
data class BaseCourseBean(
    val cname: String,
    val ctype: Int,
    val edition: Int,
    val id: Int,
    val mtype: Int,
    val num: String,
    val togo: String
):BaseEntity()