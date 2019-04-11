package com.kangce.finance.bean

/**
 * 凭证字
 */
data class CertificateBean(
    val credentcontent: String,
    val id: Int,
    val printtitle: String,
    val whetherdefault: Int
):BaseEntity()