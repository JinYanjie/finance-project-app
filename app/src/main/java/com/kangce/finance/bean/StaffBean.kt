package com.kangce.finance.bean

data class Staff(
        val list: List<StaffBean>,
        val pageNum: Int,
        val pageSize: Int,
        val total: Int,
        val totalPage: Int
)


data class StaffBean(
        val bankName: String,
        val bankNum: String,
        val birthday: String,
        val birthplace: String,
        val department: Int,
        val education: Int,
        val gender: String,
        val id: Int,
        val idCard: String,
        val inDate: String,
        val job: String,
        val name: String,
        val nation: String,
        val outDate: String,
        val phone: String,
        val position: Int,
        val state: Int
)

