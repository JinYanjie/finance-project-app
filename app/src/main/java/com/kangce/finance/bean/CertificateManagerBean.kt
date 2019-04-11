package com.kangce.finance.bean

import java.math.BigDecimal
import java.util.*

data class CertificateListBean(
        val date: Date,
        val name: String,
        val checkState: Int,
        val checkUserId: Int,
        val nameList: List<CertificateManagerBean>
) : BaseEntity()

data class CertificateManagerBean(
        var certificateNum: String?,
        var checkUserId: Int?,
        var cname: String?,
        var cnum: String?,
        var currentNum: Int?,
        var date: String?,
        var id: Int?,
        var inMoney: BigDecimal?,
        var outMoney: BigDecimal?,
        var state: Int?,
        var summary: String?,
        var type: Int?
) : BaseEntity()


